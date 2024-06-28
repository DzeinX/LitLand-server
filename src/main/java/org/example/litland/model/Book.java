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

    @Column(length = 255, nullable = false)
    private String name = ""; // required

    @Column(length = 4, nullable = false)
    private Language language = Language.RU; // required

    private Integer pages;

    @Column(nullable = false)
    private Float price = 0f; // required

    @Column(length = 20, nullable = true)
    private String ISBNNumber;

    private Boolean isNew;

    @Column(nullable = false)
    private Integer storageAmount = 0; // required

    @Column(length = 65535)
    private String description = "";

    @Column(length = 20, nullable = false)
    private String fileName;

    @Column(length = 20, nullable = false)
    private String coverName;

    @Column(nullable = false)
    private Integer publicationYear = Calendar.getInstance().get(Calendar.YEAR); // required

    private Float rating;

    private String publisher;

    private String genre;

    @Column(nullable = false)
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
