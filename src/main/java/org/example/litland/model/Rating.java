package org.example.litland.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "RATINGS")
public class Rating {
    @Id
    @GeneratedValue
    private Long id;
    private int mark;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Book.class)
    private Book book;
}
