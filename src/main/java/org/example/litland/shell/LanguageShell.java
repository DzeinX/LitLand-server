package org.example.litland.shell;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.litland.model.Language;

@Data
@AllArgsConstructor
public class LanguageShell {
    private Language code;
    private String languageName;
}
