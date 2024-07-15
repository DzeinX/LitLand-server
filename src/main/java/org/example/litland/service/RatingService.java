package org.example.litland.service;

import org.example.litland.model.Book;
import org.example.litland.model.Rating;
import org.example.litland.model.User;
import org.example.litland.repository.BookRepository;
import org.example.litland.repository.RatingRepository;
import org.example.litland.repository.UserRepository;
import org.example.litland.response.SaveMarkResponse;
import org.example.litland.response.UserMarkResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public RatingService(RatingRepository ratingRepository,
                         UserRepository userRepository,
                         BookRepository bookRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public UserMarkResponse getUserMark(String userId, String bookId) {
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if (user.isEmpty()) return new UserMarkResponse(null, null, "Пользователь не найден", "USER_NOT_FOUND");

        Optional<Book> book = bookRepository.findById(Long.valueOf(bookId));
        if (book.isEmpty()) return new UserMarkResponse(null, null, "Книга не найдена", "BOOK_NOT_FOUND");

        Optional<Rating> rating = ratingRepository.findRatingByUserIdAndBookId(Long.valueOf(userId), Long.valueOf(bookId));
        Long amountVoices = ratingRepository.countByBookId(Long.valueOf(bookId));

        if (rating.isEmpty()) return new UserMarkResponse(0, amountVoices, "", "SUCCESS");

        return new UserMarkResponse(rating.get().getMark(), amountVoices, "", "SUCCESS");
    }

    public SaveMarkResponse saveMark(String userId, String bookId, Integer mark) {
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if (user.isEmpty()) return new SaveMarkResponse("Пользователь не найден", "USER_NOT_FOUND", null, null, null);

        Optional<Book> book = bookRepository.findById(Long.valueOf(bookId));
        if (book.isEmpty()) return new SaveMarkResponse("Книга не найдена", "BOOK_NOT_FOUND", null, null, null);

        Optional<Rating> rating = ratingRepository.findRatingByUserIdAndBookId(Long.valueOf(userId), Long.valueOf(bookId));
        if (rating.isEmpty()) {
            Rating ratingDB = new Rating();
            ratingDB.setMark(mark);
            ratingDB.setUser(user.get());
            ratingDB.setBook(book.get());
            ratingRepository.save(ratingDB);
        } else {
            rating.get().setMark(mark);
            ratingRepository.save(rating.get());
        }

        Float averageRating = ratingRepository.averageRatingByBookId(Long.valueOf(bookId));
        Long amountVoices = ratingRepository.countByBookId(Long.valueOf(bookId));
        float resultRating = ((float) Math.round(averageRating * 100)) / 100;
        book.get().setRating(resultRating);
        bookRepository.save(book.get());

        return new SaveMarkResponse("", "SUCCESS", resultRating, mark, amountVoices);
    }
}
