package org.example.litland.controller;

import org.example.litland.response.CreateBookResponse;
import org.example.litland.response.SaveCoverResponse;
import org.example.litland.response.SaveFileResponse;
import org.example.litland.service.CreateBookFromClientService;
import org.example.litland.shell.BookFromClientShell;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final CreateBookFromClientService createBookFromClientService;

    public EmployeeController(CreateBookFromClientService createBookFromClientService) {
        this.createBookFromClientService = createBookFromClientService;
    }

    @GetMapping
    public void employee() {

    }

    @PostMapping("/add-book")
    @CrossOrigin(origins = "http://localhost:3000")
    public CreateBookResponse addBook(@RequestBody BookFromClientShell book) {
        return createBookFromClientService.createBookFromClient(book);
    }

    @PutMapping("/add-cover/{hash}")
    @CrossOrigin(origins = "http://localhost:3000")
    public SaveCoverResponse addCoverToBook(@RequestParam MultipartFile cover, @PathVariable String hash) {
        return createBookFromClientService.saveCoverToBook(hash, cover);
    }

    @PutMapping("/add-file/{hash}")
    @CrossOrigin(origins = "http://localhost:3000")
    public SaveFileResponse addFileToBook(@RequestParam MultipartFile file, @PathVariable String hash) {
        return createBookFromClientService.saveFileToBook(hash, file);
    }
}
