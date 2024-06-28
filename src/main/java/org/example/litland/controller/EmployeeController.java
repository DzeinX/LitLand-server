package org.example.litland.controller;

import org.example.litland.handler.error.SaveBookError;
import org.example.litland.model.Book;
import org.example.litland.repository.BookRepository;
import org.example.litland.service.BookSaveService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final BookRepository bookRepository;
    private final BookSaveService bookSaveService;

    public EmployeeController(BookRepository bookRepository,
                              BookSaveService bookSaveService) {
        this.bookRepository = bookRepository;
        this.bookSaveService = bookSaveService;
    }

    @GetMapping
    public void employee() {

    }

    @PostMapping("/add-book")
    @CrossOrigin(origins = "http://localhost:3000")
    public SaveBookError addBook(@RequestBody Book book) {
        return bookSaveService.saveBook(book);
    }
}
