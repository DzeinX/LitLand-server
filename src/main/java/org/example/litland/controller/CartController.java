package org.example.litland.controller;

import org.example.litland.response.BookCartResponse;
import org.example.litland.service.CartService;
import org.example.litland.shell.BookCartShell;
import org.example.litland.shell.BookInfoForCart;
import org.example.litland.shell.BookShell;
import org.springframework.web.bind.annotation.*;

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
    public List<BookCartShell> getCartBooks() {
        String userHash = "1"; // TODO 4 - при добавлении Spring Security поменять
        return cartService.getAllByUser(userHash);
    }

    @PostMapping("/add-new")
    @CrossOrigin(origins = "http://localhost:3000")
    public BookCartResponse addNewBookInCart(@RequestBody BookInfoForCart bookInfoForCart) {
        String userHash = "1"; // TODO 4 - при добавлении Spring Security поменять
        return cartService.addNewBook(userHash, bookInfoForCart.getHash());
    }

    @PutMapping("/add-one")
    @CrossOrigin(origins = "http://localhost:3000")
    public BookCartResponse addOneBookInCart(@RequestBody BookInfoForCart bookInfoForCart) {
        String userHash = "1"; // TODO 4 - при добавлении Spring Security поменять
        return cartService.addOneBook(userHash, bookInfoForCart.getHash());
    }

    @PutMapping("/remove-one")
    @CrossOrigin(origins = "http://localhost:3000")
    public BookCartResponse removeOneBookFromTheCart(@RequestBody BookInfoForCart bookInfoForCart) {
        String userHash = "1"; // TODO 4 - при добавлении Spring Security поменять
        return cartService.removeOneBook(userHash, bookInfoForCart.getHash());
    }

    @DeleteMapping("/remove")
    @CrossOrigin(origins = "http://localhost:3000")
    public BookCartResponse removeBookFromTheCart(@RequestBody BookInfoForCart bookInfoForCart) {
        String userHash = "1"; // TODO 4 - при добавлении Spring Security поменять
        return cartService.removeBook(userHash, bookInfoForCart.getHash());
    }
}
