package org.example.litland.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "RATING")
public class Rating {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Book.class)
    private Book book;
}
