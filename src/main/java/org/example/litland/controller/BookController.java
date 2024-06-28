package org.example.litland.controller;

import org.example.litland.model.Book;
import org.example.litland.repository.BookRepository;
import org.example.litland.response.BookResponse;
import org.example.litland.service.BookToClientService;
import org.example.litland.shell.BookShell;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class BookController {
    private final BookToClientService bookToClientService;


    public BookController(BookToClientService bookToClientService) {
        this.bookToClientService = bookToClientService;
    }

    @GetMapping("/")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<BookShell> getAllBooks() {
        return bookToClientService.getAllBooksToClient();
    }

    @GetMapping("/book/{hash}")
    @CrossOrigin(origins = "http://localhost:3000")
    public BookResponse getBookByHash(@PathVariable String hash) {
        return bookToClientService.getBookToClientFromHash(hash);
    }
}
