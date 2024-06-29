package org.example.litland.service;

import org.example.litland.model.Book;
import org.example.litland.repository.BookRepository;
import org.example.litland.response.BookResponse;
import org.example.litland.shell.BookShell;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookToClientService {
    private final BookRepository bookRepository;

    private final String SECRET_VALUE = "jhlska8275098hshd00827"; // TODO 1 - закодировать с помощью SECRET_VALUE

    public BookToClientService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public boolean checkIfExist(Long id) {
        return bookRepository.findById(id).isPresent();
    }

    private int getStorageAmountCode(Integer storageAmount) {
        if (storageAmount == null) return -1;

        if (storageAmount < 1) {
            return 0; // "Товар закончился"
        } else if (storageAmount > 5) {
            return 2; // "Товара много"
        } else {
            return 1; // "Товара мало"
        }
    }

    public BookShell getBookShellFromBookEntity(Book book) {
        BookShell bookShell = new BookShell();
        // TODO 1 - закодировать с помощью SECRET_VALUE
        bookShell.setHash(book.getId().toString());
        bookShell.setName(book.getName());
        bookShell.setLanguage(book.getLanguage());
        bookShell.setPages(book.getPages());
        bookShell.setPrice(book.getPrice());
        bookShell.setISBNNumber(book.getISBNNumber());
        bookShell.setIsNew(book.getIsNew());
        bookShell.setStorageStatus(this.getStorageAmountCode(book.getStorageAmount()));
        bookShell.setDescription(book.getDescription());
        bookShell.setPublicationYear(book.getPublicationYear());
        bookShell.setRating(book.getRating());
        bookShell.setPublisher(book.getPublisher());
        bookShell.setGenre(book.getGenre());
        bookShell.setAuthors(book.getAuthors());
        bookShell.setCoverName(book.getCoverName());

        bookShell.setIsDigital(book.getFileName() != null);

        return bookShell;
    }

    public List<BookShell> getAllBooksToClient() {
        List<BookShell> bookShells = new ArrayList<>();
        bookRepository.findAll().forEach(book -> {
            bookShells.add(getBookShellFromBookEntity(book));
        });

        return bookShells;
    }

    public BookResponse getBookToClientFromHash(String Hash) {
        // TODO 2 - декодирование Hash в id
        Long id = Long.valueOf(Hash);

        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            BookShell bookShell = this.getBookShellFromBookEntity(bookOptional.get());
            return new BookResponse(bookShell, "Книга найдена", "FOUND");
        }

        return new BookResponse(null, "Книга не найдена", "NOT_FOUND");
    }
}
