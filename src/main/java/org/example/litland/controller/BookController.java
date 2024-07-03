package org.example.litland.controller;

import org.example.litland.response.AmountBookPagesResponse;
import org.example.litland.response.BookResponse;
import org.example.litland.response.PaginatedBooksResponse;
import org.example.litland.service.BookToClientService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class BookController {
    private final BookToClientService bookToClientService;

    public BookController(BookToClientService bookToClientService) {
        this.bookToClientService = bookToClientService;
    }

    @GetMapping(path="/", params={"page", "size"})
    @CrossOrigin(origins = "http://localhost:3000")
    public PaginatedBooksResponse getAllBooks(@RequestParam int page, @RequestParam int size) {
        return bookToClientService.getAllBooksToClientPaginated(page, size);
    }

    @GetMapping(path="/book/amount-pages", params={"size"})
    @CrossOrigin(origins = "http://localhost:3000")
    public AmountBookPagesResponse getAmountPages(@RequestParam int size) {
        return bookToClientService.getBooksAmountPages(size);
    }

    @GetMapping("/book/{hash}")
    @CrossOrigin(origins = "http://localhost:3000")
    public BookResponse getBookByHash(@PathVariable String hash) {
        return bookToClientService.getBookToClientFromHash(hash);
    }
}
