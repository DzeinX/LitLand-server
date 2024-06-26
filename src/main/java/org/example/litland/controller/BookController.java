package org.example.litland.controller;

import org.example.litland.model.Book;
import org.example.litland.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class BookController {
    private final BookRepository bookRepository;


    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/add-book")
    @CrossOrigin(origins = "http://localhost:3000")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }
}
