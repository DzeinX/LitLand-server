package org.example.litland;

import org.example.litland.model.*;
import org.example.litland.repository.BookRepository;
import org.example.litland.repository.GenreRepository;
import org.example.litland.repository.PublisherRepository;
import org.example.litland.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

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

        addUser("LitLand", "LitLand");
        addUser("LitLand2", "LitLand2");
        Genre genre1 = addGenre("Психология");
        Genre genre2 = addGenre("Наука");
        Genre genre3 = addGenre("Художественная литература");

        Publisher publisher1 = addPublisher("Арма");
        Publisher publisher2 = addPublisher("Синдбад");

        for (int i = 0; i < 10; i++) {
            addBook((i + 1) + " Книга",
                    "Описние книги",
                    Language.RU,
                    1490f - new Random().nextInt(1000),
                    30 * (i + 1) - new Random().nextInt(10),
                    true,
                    100 - new Random().nextInt(100),
                    2024 - new Random().nextInt(10),
                    10f - new Random().nextInt(5),
                    "9090-9090-90-9",
                    "автор книги",
                    i % 2 == 0 ? "default.png" : "book_4.jpg",
                    genre1,
                    publisher1
            );
        }

        addBook("После бури",
                "«После бури» – заключительная часть трилогии о жителях двух соперничающих хоккейных городов.",
                Language.RU,
                449f,
                300,
                true,
                30,
                2024,
                9f,
                "978-5-00131-586-5",
                "Фредрик Бакман",
                "book_2.jpg",
                genre3,
                publisher2
        );

    }

    private static void addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
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

    private static void addBook(String name,
                                     String description,
                                     Language language,
                                     Float price,
                                     Integer pages,
                                     Boolean isNew,
                                     Integer storageAmount,
                                     Integer publicationYear,
                                     Float rating,
                                     String ISBNNumber,
                                     String authors,
                                     String coverName,
                                     Genre genre,
                                     Publisher publisher
    ) {
        Book book = new Book();
        book.setName(name);
        book.setDescription(description);
        book.setLanguage(language);
        book.setPrice(price);
        book.setPages(pages);
        book.setIsNew(isNew);
        book.setStorageAmount(storageAmount);
        book.setPublicationYear(publicationYear);
        book.setRating(rating);
        book.setISBNNumber(ISBNNumber);
        book.setAuthors(authors);
        book.setPublisher(publisher);
        book.setGenre(genre);
        book.setCoverName(coverName);
        bookRepository.save(book);
    }

}
