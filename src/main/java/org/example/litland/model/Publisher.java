package org.example.litland.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "PUBLISHER")
public class Publisher {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
