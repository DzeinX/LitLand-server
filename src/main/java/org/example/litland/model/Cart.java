package org.example.litland.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "CARTS")
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Book.class)
    private Book book;

    @ManyToOne(targetEntity = Order.class)
    private Order order;

    @ManyToOne(targetEntity = User.class)
    private User user;

    private int amount = 1;
    private CartStatus status;
    private Date datePurchasing;
    private Date dateAddingToCart = new Date();
    private Date dateGetting;
}
