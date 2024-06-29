package org.example.litland.controller;

import org.example.litland.handler.error.SaveBookError;
import org.example.litland.model.Book;
import org.example.litland.repository.BookRepository;
import org.example.litland.response.CreateBookResponse;
import org.example.litland.response.SaveCoverResponse;
import org.example.litland.response.SaveFileResponse;
import org.example.litland.service.BookSaveService;
import org.example.litland.service.CreateBookFromClientService;
import org.example.litland.shell.BookFromClientShell;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final CreateBookFromClientService createBookFromClientService;
    private final BookSaveService bookSaveService;

    public EmployeeController(CreateBookFromClientService createBookFromClientService,
                              BookSaveService bookSaveService) {
        this.createBookFromClientService = createBookFromClientService;
        this.bookSaveService = bookSaveService;
    }

    @GetMapping
    public void employee() {

    }

    @PostMapping("/add-book")
    @CrossOrigin(origins = "http://localhost:3000")
    public CreateBookResponse addBook(@RequestBody BookFromClientShell book) {
        return createBookFromClientService.createBookFromClient(book);
    }

    @PostMapping("/add-cover/{hash}")
    @CrossOrigin(origins = "http://localhost:3000")
    public SaveCoverResponse addCoverToBook(@RequestParam MultipartFile cover, @PathVariable String hash) {
        return createBookFromClientService.saveCoverToBook(hash, cover);
    }

    @PostMapping("/add-file/{hash}")
    @CrossOrigin(origins = "http://localhost:3000")
    public SaveFileResponse addFileToBook(@RequestParam MultipartFile file, @PathVariable String hash) {
        return createBookFromClientService.saveFileToBook(hash, file);
    }
}
