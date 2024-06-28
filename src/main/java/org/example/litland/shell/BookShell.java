package org.example.litland.shell;

import lombok.Data;
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

    private Float rating;

    private String publisher;

    private String genre;

    private String authors;
}
