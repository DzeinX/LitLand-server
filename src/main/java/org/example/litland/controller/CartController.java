package org.example.litland.controller;

import org.example.litland.service.CartService;
import org.example.litland.shell.BookShell;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<BookShell> getCartBooks() {
        String userHash = "1"; // TODO 4 - при добавлении Spring Security поменять
        return cartService.getAllByUser(userHash);
    }
}
