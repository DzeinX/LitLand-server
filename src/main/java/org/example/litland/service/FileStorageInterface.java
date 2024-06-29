package org.example.litland.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileStorageInterface {
    boolean save(MultipartFile file, String fileName, Integer mode);
}
