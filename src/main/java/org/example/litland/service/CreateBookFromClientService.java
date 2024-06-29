package org.example.litland.service;

import org.example.litland.model.Book;
import org.example.litland.repository.BookRepository;
import org.example.litland.response.CreateBookResponse;
import org.example.litland.response.SaveCoverResponse;
import org.example.litland.response.SaveFileResponse;
import org.example.litland.shell.BookFromClientShell;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Service
public class CreateBookFromClientService {
    private final BookRepository bookRepository;
    private final FileStorageBookService fileStorageBookService;
    
    public CreateBookFromClientService(BookRepository bookRepository, FileStorageBookService fileStorageBookService) {
        this.bookRepository = bookRepository;
        this.fileStorageBookService = fileStorageBookService;
    }

    public SaveCoverResponse saveCoverToBook(String bookHash, MultipartFile bookCover) {
        Optional<Book> book = bookRepository.findById(Long.valueOf(bookHash));
        if (book.isEmpty()) {
            return new SaveCoverResponse("Книга не найдена", "NOT_FOUND");
        }

        String fileName = "book_" + bookHash;
        CreateBookResponse coverResponse = this.tryToSaveCover(fileName, bookCover);
        if (coverResponse.getVerdict().equals("ERROR_SAVE_COVER")) {
            return new SaveCoverResponse("Не удалось сохранить обложку", "ERROR_WITH_SAVE_COVER");
        } else if (coverResponse.getVerdict().equals("SUCCESS")) {
            book.get().setCoverName(coverResponse.getMessage());
            bookRepository.save(book.get());
            return new SaveCoverResponse("Обложка успешно сохранена", "SUCCESS");
        }
        return new SaveCoverResponse("Файл обложки не распознан", "COVER_NOT_FOUND");
    }

    public SaveFileResponse saveFileToBook(String bookHash, MultipartFile bookFile) {
        Optional<Book> book = bookRepository.findById(Long.valueOf(bookHash));
        if (book.isEmpty()) {
            return new SaveFileResponse("Книга не найдена", "NOT_FOUND");
        }

        String fileName = "book_" + bookHash;
        CreateBookResponse fileResponse = this.tryToSaveFile(fileName, bookFile);
        if (fileResponse.getVerdict().equals("ERROR_SAVE_COVER")) {
            return new SaveFileResponse("Не удалось сохранить файл", "ERROR_WITH_SAVE_FILE");
        } else if (fileResponse.getVerdict().equals("SUCCESS")) {
            book.get().setFileName(fileResponse.getMessage());
            bookRepository.save(book.get());
            return new SaveFileResponse("Файл успешно сохранена", "SUCCESS");
        }
        return new SaveFileResponse("Файл книги не распознан", "FILE_NOT_FOUND");
    }
    
    public CreateBookResponse createBookFromClient(BookFromClientShell bookFromClientShell) {
        Book book = getBook(bookFromClientShell);
        book = bookRepository.save(book);

        // TODO - id в hash
        return new CreateBookResponse("", "SUCCESS", book.getId().toString());
    }

    private static Book getBook(BookFromClientShell bookFromClientShell) {
        Book book = new Book();

        book.setName(bookFromClientShell.getName());
        book.setDescription(bookFromClientShell.getDescription());
        book.setLanguage(bookFromClientShell.getLanguage());
        book.setPrice(bookFromClientShell.getPrice());
        book.setPages(bookFromClientShell.getPages());
        book.setIsNew(bookFromClientShell.getIsNew());
        book.setStorageAmount(bookFromClientShell.getStorageAmount());
        book.setPublicationYear(bookFromClientShell.getPublicationYear());
        book.setRating(bookFromClientShell.getRating());
        book.setISBNNumber(bookFromClientShell.getISBNNumber());

        // TODO 5 - заменить как только добавятся связи в БД
        book.setAuthors(bookFromClientShell.getAuthors());
        book.setPublisher(bookFromClientShell.getPublisher());
        book.setGenre(bookFromClientShell.getGenre());
        return book;
    }

    private CreateBookResponse tryToSaveCover(String fileName, MultipartFile bookCover) {
        if (!bookCover.isEmpty()) {
            String[] nameArray = Objects.requireNonNull(bookCover.getOriginalFilename()).split("[.]");
            String type = nameArray[nameArray.length - 1];
            if (!fileStorageBookService.save(bookCover, fileName + "." + type, fileStorageBookService.COVER)) {
                return new CreateBookResponse("Не удалось сохранить файл обложки. Но книга была сохранена", "ERROR_SAVE_COVER", "");
            }
            return new CreateBookResponse(fileName + "." + type, "SUCCESS", "");
        }
        return new CreateBookResponse("", "NO_COVER", "");
    }

    private CreateBookResponse tryToSaveFile(String fileName, MultipartFile bookFile) {
        if (!bookFile.isEmpty()) {
            String[] nameArray = Objects.requireNonNull(bookFile.getOriginalFilename()).split("[.]");
            String type = nameArray[nameArray.length - 1];
            if (!fileStorageBookService.save(bookFile, fileName + "." + type, fileStorageBookService.FILE)) {
                return new CreateBookResponse("Не удалось сохранить файл книги. Но книга была сохранена", "ERROR_SAVE_FILE", "");
            }
            return new CreateBookResponse(fileName + "." + type, "SUCCESS", "");
        }
        return new CreateBookResponse("", "NO_FILE", "");
    }
}
