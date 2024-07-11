package org.example.litland.controller;

import org.example.litland.response.BookCartResponse;
import org.example.litland.response.OrderResponse;
import org.example.litland.service.CartService;
import org.example.litland.shell.BookCartShell;
import org.example.litland.shell.BookInfoForCart;
import org.example.litland.shell.OrderCarts;
import org.example.litland.shell.UserInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<BookCartShell> getCartBooks(@RequestBody UserInfo userInfo) {
        return cartService.getAllByUser(userInfo.getUserId());
    }

    @PostMapping("/add-new")
    @CrossOrigin(origins = "http://localhost:3000")
    public BookCartResponse addNewBookInCart(@RequestBody BookInfoForCart bookInfoForCart) {
        return cartService.addNewBook(bookInfoForCart.getUserId(), bookInfoForCart.getHash());
    }

    @PutMapping("/add-one")
    @CrossOrigin(origins = "http://localhost:3000")
    public BookCartResponse addOneBookInCart(@RequestBody BookInfoForCart bookInfoForCart) {
        return cartService.addOneBook(bookInfoForCart.getUserId(), bookInfoForCart.getHash());
    }

    @PutMapping("/remove-one")
    @CrossOrigin(origins = "http://localhost:3000")
    public BookCartResponse removeOneBookFromTheCart(@RequestBody BookInfoForCart bookInfoForCart) {
        return cartService.removeOneBook(bookInfoForCart.getUserId(), bookInfoForCart.getHash());
    }

    @DeleteMapping("/remove")
    @CrossOrigin(origins = "http://localhost:3000")
    public BookCartResponse removeBookFromTheCart(@RequestBody BookInfoForCart bookInfoForCart) {
        return cartService.removeBook(bookInfoForCart.getUserId(), bookInfoForCart.getHash());
    }

    @PostMapping("/order")
    @CrossOrigin(origins = "http://localhost:3000")
    public OrderResponse orderBooks(@RequestBody OrderCarts orderCarts) {
        return cartService.order(orderCarts.getHash(), orderCarts.getCarts());
    }
}
