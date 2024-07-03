package org.example.litland.shell;

import lombok.Data;
import org.example.litland.model.Genre;
import org.example.litland.model.Language;

@Data
public class BookShell {
    private String hash; // аналог id

    private String name;

    private Language language;

    private Integer pages;

    private Float price;

    private String ISBNNumber;

    private Boolean isNew;

    private Integer storageStatus; // аналог hash

    private String description;

    private Integer publicationYear;

    private String coverName;

    private Boolean isDigital;

    private Float rating;

    private PublisherShell publisher;

    private GenreShell genre;

    private String authors;
}
