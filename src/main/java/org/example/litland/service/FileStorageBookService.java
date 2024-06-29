package org.example.litland.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.commons.io.filefilter.WildcardFileFilter;

@Service
public class FileStorageBookService implements FileStorageInterface {
    private static final String PATH_TO_BOOK_COVER  = new FileSystemResource("").getFile().getAbsolutePath() + "/upload/cover/";
    private static final String PATH_TO_BOOK_FILE  = new FileSystemResource("").getFile().getAbsolutePath() + "/upload/file/";
    private static final List<String> VALID_FILE_TYPES_FILES = List.of("pdf", "docx", "doc", "epub", "fb2", "mobi", "kf8", "azw", "lrf");
    private static final List<String> VALID_FILE_TYPES_COVERS = List.of("jpg", "png", "ico");

    public final Integer COVER = 0;
    public final Integer FILE = 1;

    @Override
    public boolean save(MultipartFile file, String fileName, Integer mode) {
        String pathMode = mode == 0 ? PATH_TO_BOOK_COVER : PATH_TO_BOOK_FILE;
        List<String> validTypes = mode == 0 ? VALID_FILE_TYPES_COVERS : VALID_FILE_TYPES_FILES;
        String[] nameArray = fileName.split("[.]");
        String name = nameArray[0];
        String type = nameArray[1];
        if (!validTypes.contains(type)) {
            return false;
        }
        deleteFile(name, mode);

        Path path = Paths.get(pathMode + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void deleteFile(String nameWithoutType, Integer mode) {
        String path = mode == 0 ? PATH_TO_BOOK_COVER : PATH_TO_BOOK_FILE;
        FileFilter fileFilter = new WildcardFileFilter(nameWithoutType + ".*");
        File dir = new File(path);
        File[] files = dir.listFiles(fileFilter);
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
    }
}
