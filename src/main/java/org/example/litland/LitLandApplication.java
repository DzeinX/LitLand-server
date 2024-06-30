package org.example.litland;

import org.example.litland.model.Book;
import org.example.litland.model.Language;
import org.example.litland.model.User;
import org.example.litland.repository.BookRepository;
import org.example.litland.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LitLandApplication {
    private static UserRepository userRepository;
    private static BookRepository bookRepository;

    public LitLandApplication(UserRepository userRepository,
                              BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(LitLandApplication.class, args);

        addFirstUser();
        addFirstBook();
        addFirstBook();
    }

    private static void addFirstUser() {
        User user = new User();
        user.setUsername("LitLand");
        user.setPassword("LitLand");
        userRepository.save(user);
    }

    private static void addFirstBook() {
        Book book = new Book();
        book.setName("книга");
        book.setDescription("описние книги");
        book.setLanguage(Language.RU);
        book.setPrice(349f);
        book.setPages(30);
        book.setIsNew(true);
        book.setStorageAmount(3);
        book.setPublicationYear(2024);
        book.setRating(10f);
        book.setISBNNumber("9090-9090-90-9");
        book.setAuthors("автор книги");
        book.setPublisher("издатель книги");
        book.setGenre("жанр книги");
        book.setCoverName("book_1.ico");
        bookRepository.save(book);
    }

}
