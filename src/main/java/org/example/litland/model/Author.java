package org.example.litland.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "AUTHOR")
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
}
