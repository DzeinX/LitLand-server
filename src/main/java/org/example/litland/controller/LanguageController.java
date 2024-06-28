package org.example.litland.controller;

import org.example.litland.model.Language;
import org.example.litland.shell.LanguageShell;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/language")
public class LanguageController {
    private Map<Language, String> languageMapping = new HashMap<>();

    public LanguageController() {
        languageMapping.put(Language.RU, "Русский");
        languageMapping.put(Language.FR, "Французкий");
        languageMapping.put(Language.GR, "Немецкий");
        languageMapping.put(Language.EN, "Английский (США)");
        languageMapping.put(Language.UK, "Английский (Англия)");
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<LanguageShell> getLanguages() {
        List<LanguageShell> languages = new ArrayList<>();

        for (var entry : languageMapping.entrySet()) {
            languages.add(new LanguageShell(entry.getKey(), entry.getValue()));
        }

        return languages;
    }
}
