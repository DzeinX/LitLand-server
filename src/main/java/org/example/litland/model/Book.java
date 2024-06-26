package org.example.litland.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "BOOKS")
@Data
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Language language;

    private Integer pages;

    private Float price;

    private String ISBNNumber;

    private Boolean isNew;

    private Integer storageAmount;

    private String description = "";

    private String fileName;

    private String coverName;

    private Integer publicationYear;

    private Float rating;

    private String publisher;

    private String genre;

    private String authors;

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
