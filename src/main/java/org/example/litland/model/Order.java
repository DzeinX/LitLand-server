package org.example.litland.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="ORDERS")
@Data
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(targetEntity = Cart.class)
    private List<Cart> carts;

    @ManyToOne(targetEntity = User.class)
    private User user;
}
