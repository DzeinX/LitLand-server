package org.example.litland.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Calendar;
import java.util.List;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String name = ""; // required

    private Language language = Language.RU; // required

    private Integer pages;

    private Float price = 0f; // required

    private String ISBNNumber;

    private Boolean isNew;

    private Integer storageAmount = 0; // required

    private String description = "";

    private String fileName;

    private String coverName;

    private Integer publicationYear = Calendar.getInstance().get(Calendar.YEAR); // required

    private Float rating;

    private String publisher;

    private String genre;

    private String authors = ""; // required

//    @ManyToOne(targetEntity = Publisher.class)
//    private Publisher publisher;
//
//    @ManyToOne(targetEntity = Genre.class)
//    private Genre genre;
//
//    @ManyToMany
//    @JoinTable(name = "authors_books", joinColumns = @JoinColumn(name = "books_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
//    List<Author> authors;
}
