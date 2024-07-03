package org.example.litland.controller;

import org.example.litland.service.GenreService;
import org.example.litland.shell.GenreShell;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<GenreShell> getGenres() {
        return genreService.getAllGenres();
    }
}
