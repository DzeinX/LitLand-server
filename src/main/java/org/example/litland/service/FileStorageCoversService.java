package org.example.litland.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileStorageCoversService {
    private static final String PATH_TO_BOOK_COVER  = new FileSystemResource("").getFile().getAbsolutePath() + "/upload/cover/";

    public byte[] load(String fileName) {
        if (fileName.equals("null")) return this.loadDefault();

        return this.loadByName(fileName);
    }

    private byte[] loadByName(String fileName) {
        File serverFile = new File(PATH_TO_BOOK_COVER + fileName);
        try {
            return Files.readAllBytes(serverFile.toPath());
        } catch (IOException e) {
            return new byte[0];
        }
    }

    private byte[] loadDefault() {
        File serverFile = new File(PATH_TO_BOOK_COVER + "default.png");
        try {
            return Files.readAllBytes(serverFile.toPath());
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
