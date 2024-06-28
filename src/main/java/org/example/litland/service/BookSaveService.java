package org.example.litland.service;

import org.example.litland.handler.error.SaveBookError;
import org.example.litland.model.Book;
import org.example.litland.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class BookSaveService {
    private static final Logger log = LoggerFactory.getLogger(BookSaveService.class);
    private final BookRepository bookRepository;

    public BookSaveService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public SaveBookError saveBook(Book book) {
        try {
            bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            return new SaveBookError("Internal error", 100);
        }

        return new SaveBookError("", -1);
    }
}
