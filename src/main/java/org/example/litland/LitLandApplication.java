package org.example.litland;

import org.example.litland.model.*;
import org.example.litland.repository.BookRepository;
import org.example.litland.repository.GenreRepository;
import org.example.litland.repository.PublisherRepository;
import org.example.litland.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LitLandApplication {
    private static UserRepository userRepository;
    private static BookRepository bookRepository;
    private static GenreRepository genreRepository;
    private static PublisherRepository publisherRepository;

    public LitLandApplication(UserRepository userRepository,
                              BookRepository bookRepository,
                              GenreRepository genreRepository,
                              PublisherRepository publisherRepository) {
        LitLandApplication.userRepository = userRepository;
        LitLandApplication.bookRepository = bookRepository;
        LitLandApplication.genreRepository = genreRepository;
        LitLandApplication.publisherRepository = publisherRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(LitLandApplication.class, args);

        addFirstUser();
        Genre genre1 = addGenre("Психология");
        Genre genre2 = addGenre("Наука");
        Genre genre3 = addGenre("Фентази");
        Publisher publisher1 = addPublisher("Арма");
        Publisher publisher2 = addPublisher("Цыфра");
        addFirstBook(genre1, publisher2);
        addFirstBook(genre2, publisher1);
        addFirstBook(genre3, publisher1);

    }

    private static void addFirstUser() {
        User user = new User();
        user.setUsername("LitLand");
        user.setPassword("LitLand");
        userRepository.save(user);
    }

    private static Genre addGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        return genreRepository.save(genre);
    }

    private static Publisher addPublisher(String name) {
        Publisher publisher = new Publisher();
        publisher.setName(name);
        return publisherRepository.save(publisher);
    }

    private static void addFirstBook(Genre genre, Publisher publisher) {
        Book book = new Book();
        book.setName("Убийства и кексики. Детективное агентство «Благотворительный магазин»");
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
        book.setPublisher(publisher);
        book.setGenre(genre);
        book.setCoverName("book_1.ico");
        bookRepository.save(book);
    }

}
