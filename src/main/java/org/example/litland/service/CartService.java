package org.example.litland.service;

import org.example.litland.model.Cart;
import org.example.litland.repository.CartRepository;
import org.example.litland.shell.BookShell;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final BookToClientService bookToClientService;
    private final CartRepository cartRepository;

    public CartService(BookToClientService bookToClientService, CartRepository cartRepository) {
        this.bookToClientService = bookToClientService;
        this.cartRepository = cartRepository;
    }

    private Long decodeUserHash(String hash) {
        // TODO 3 - Сделать декодер для id пользователя
        return Long.parseLong(hash);
    }

    public List<BookShell> getAllByUser(String userHash) {
        List<BookShell> bookShells = new ArrayList<>();
        List<Cart> carts = cartRepository.findByUserId(this.decodeUserHash(userHash));
        carts.forEach(cart -> {
            bookShells.add(bookToClientService.getBookShellFromBookEntity(cart.getBook()));
        });

        return bookShells;
    }
}
