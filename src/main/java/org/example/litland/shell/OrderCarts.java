package org.example.litland.shell;

import lombok.Data;
import org.example.litland.model.Cart;

import java.util.List;

@Data
public class OrderCarts {
    private List<BookCartShell> carts;
    private String hash;
}
