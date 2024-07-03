package org.example.litland.controller;

import org.example.litland.service.FileStorageCoversService;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/book")
public class UploadLoaderController {
    private final FileStorageCoversService fileStorageCoversService;

    public UploadLoaderController(FileStorageCoversService fileStorageCoversService) {
        this.fileStorageCoversService = fileStorageCoversService;
    }

    @GetMapping("/image/{imageName}")
    @CrossOrigin(origins = "http://localhost:3000")
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) {
        return fileStorageCoversService.load(imageName);
    }
}
