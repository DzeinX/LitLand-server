package org.example.litland.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
public class FileStorageCoversService {
    private static final String PATH_TO_BOOK_COVER  = new FileSystemResource("").getFile().getAbsolutePath() + "/upload/cover/";

    public File load(String fileName) {
        if (Objects.equals(fileName, "null")) {
            return new File(PATH_TO_BOOK_COVER + "default.png");
        }
        return new File(PATH_TO_BOOK_COVER + fileName);
    }
}
