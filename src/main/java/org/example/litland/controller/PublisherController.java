package org.example.litland.controller;

import org.example.litland.service.PublisherService;
import org.example.litland.shell.PublisherShell;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<PublisherShell> getGenres() {
        return publisherService.getAllPublishers();
    }
}
