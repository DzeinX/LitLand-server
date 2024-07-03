package org.example.litland.service;

import org.example.litland.model.Genre;
import org.example.litland.repository.GenreRepository;
import org.example.litland.shell.GenreShell;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreShell> getAllGenres() {
        List<GenreShell> genres = new ArrayList<>();
        genreRepository.findAll().forEach((genre) -> {
            genres.add(this.getGenre(genre));
        });

        return genres;
    }

    private GenreShell getGenre(Genre genre) {
        return new GenreShell(genre.getId().toString(), genre.getName());
    }
}
