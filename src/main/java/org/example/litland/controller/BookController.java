package org.example.litland.controller;

import org.example.litland.response.BookResponse;
import org.example.litland.service.BookToClientService;
import org.example.litland.shell.BookShell;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class BookController {
    private final BookToClientService bookToClientService;


    public BookController(BookToClientService bookToClientService) {
        this.bookToClientService = bookToClientService;
    }

    @GetMapping(path="/", params={"page", "size"})
    @CrossOrigin(origins = "http://localhost:3000")
    public List<BookShell> getAllBooks(@RequestParam int page, @RequestParam int size) {
        if (size <= 0) return new ArrayList<>();
        return bookToClientService.getAllBooksToClientPaginated(page, size);
    }

    @GetMapping(path="/book/amount-pages", params={"size"})
    @CrossOrigin(origins = "http://localhost:3000")
    public int getAmountPages(@RequestParam int size) {
        if (size <= 0) return -1;
        return bookToClientService.getBooksAmountPages(size);
    }

    @GetMapping("/book/{hash}")
    @CrossOrigin(origins = "http://localhost:3000")
    public BookResponse getBookByHash(@PathVariable String hash) {
        return bookToClientService.getBookToClientFromHash(hash);
    }
}
