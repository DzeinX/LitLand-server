package org.example.litland.shell;


import lombok.Data;
import org.example.litland.model.Language;

@Data
public class BookFromClientShell {
    private String name; // required

    private Language language; // required

    private Integer pages;

    private Float price; // required

    private String ISBNNumber;

    private Boolean isNew;

    private Integer storageAmount; // required

    private String description;

    private Integer publicationYear;

    private Float rating;

    private String publisher;

    private String genre;

    private String authors; // required
}
