package org.example.litland.service;

import org.example.litland.model.Book;
import org.example.litland.repository.BookRepository;
import org.example.litland.repository.GenreRepository;
import org.example.litland.response.AmountBookPagesResponse;
import org.example.litland.response.BookResponse;
import org.example.litland.response.PaginatedBooksResponse;
import org.example.litland.shell.BookShell;
import org.example.litland.shell.GenreShell;
import org.example.litland.shell.PublisherShell;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        bookShell.setPublisher(new PublisherShell(book.getId().toString(), book.getPublisher().getName()));
        bookShell.setGenre(new GenreShell(book.getId().toString(), book.getGenre().getName()));
        bookShell.setAuthors(book.getAuthors());
        bookShell.setCoverName(book.getCoverName());

        bookShell.setIsDigital(book.getFileName() != null);

        return bookShell;
    }

    public PaginatedBooksResponse getAllBooksToClientPaginated(int number, int size) {
        if (size <= 0) return new PaginatedBooksResponse(new ArrayList<>(), "Размер страницы меньше еденицы", "SIZE_PAGE_LESS_THEN_ONE");
        List<BookShell> bookShells = new ArrayList<>();
        Pageable page = PageRequest.of(number, size);
        bookRepository.findAll(page).forEach(book -> {
            bookShells.add(getBookShellFromBookEntity(book));
        });

        return new PaginatedBooksResponse(bookShells, "", "SUCCESS");
    }

    public AmountBookPagesResponse getBooksAmountPages(int size) {
        if (size <= 0) return new AmountBookPagesResponse(null, "Размер страницы меньше еденицы", "SIZE_PAGE_LESS_THEN_ONE");
        Pageable page = PageRequest.of(0, size);
        Integer totalPages = bookRepository.findAll(page).getTotalPages();
        return new AmountBookPagesResponse(totalPages, "", "SUCCESS");
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
