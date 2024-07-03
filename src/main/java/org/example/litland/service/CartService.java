package org.example.litland.service;

import org.example.litland.model.Book;
import org.example.litland.model.Cart;
import org.example.litland.model.CartStatus;
import org.example.litland.model.User;
import org.example.litland.repository.BookRepository;
import org.example.litland.repository.CartRepository;
import org.example.litland.repository.UserRepository;
import org.example.litland.response.BookCartResponse;
import org.example.litland.shell.BookCartShell;
import org.example.litland.shell.BookShell;
import org.example.litland.shell.UserAndBook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       BookRepository bookRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    private Long decodeUserHash(String hash) {
        // TODO 3 - Сделать декодер для id пользователя
        return Long.parseLong(hash);
    }

    public List<BookCartShell> getAllByUser(String userHash) {
        List<BookCartShell> bookShells = new ArrayList<>();
        List<Cart> carts = cartRepository.findByUserId(this.decodeUserHash(userHash));
        carts.forEach(cart -> {
            BookCartShell bookCartShell = new BookCartShell(
                    cart.getBook().getId().toString(),
                    cart.getBook().getName(),
                    cart.getBook().getAuthors(),
                    cart.getAmount(),
                    cart.getBook().getPrice(),
                    cart.getBook().getCoverName()
            );
            bookShells.add(bookCartShell);
        });

        return bookShells;
    }

    private UserAndBook checkUserAndBookExists(String userHash, String bookHash) {
        Optional<User> user = userRepository.findById(Long.parseLong(userHash));
        if (user.isEmpty()) {
            BookCartResponse bookCartResponse = new BookCartResponse(null, "Пользователь не найден", "USER_NOT_FOUND");
            return new UserAndBook(null, null, bookCartResponse);
        }

        Optional<Book> book = bookRepository.findById(Long.parseLong(bookHash));
        if (book.isEmpty()) {
            BookCartResponse bookCartResponse = new BookCartResponse(null, "Книга не найдена", "BOOK_NOT_FOUND");
            return new UserAndBook(user.get(), null, bookCartResponse);
        }

        BookCartResponse bookCartResponse = new BookCartResponse(null, "", "SUCCESS");
        return new UserAndBook(user.get(), book.get(), bookCartResponse);
    }

    public BookCartResponse addNewBook(String userHash, String bookHash) {
        UserAndBook checkAnswer = this.checkUserAndBookExists(userHash, bookHash);
        if (!checkAnswer.getBookCartResponse().getVerdict().equals("SUCCESS")) return checkAnswer.getBookCartResponse();

        Optional<Cart> cartExist = cartRepository.findByUserIdAndBookId(Long.parseLong(userHash), Long.parseLong(bookHash));
        if (cartExist.isPresent()) return new BookCartResponse(null, "Книга уже в корзине", "BOOK_IN_CART");

        Cart cartSaved;
        try {
            Cart cart = new Cart();
            cart.setBook(checkAnswer.getBook());
            cart.setUser(checkAnswer.getUser());
            cart.setStatus(CartStatus.IN_CART);
            cartSaved = cartRepository.save(cart);
        } catch (Exception e) {
            return new BookCartResponse(null, "Ошибка сохранения", "ERROR_SAVE");
        }

        BookCartShell bookCartShell = new BookCartShell(
                checkAnswer.getBook().getId().toString(),
                checkAnswer.getBook().getName(),
                checkAnswer.getBook().getAuthors(),
                cartSaved.getAmount(),
                checkAnswer.getBook().getPrice(),
                checkAnswer.getBook().getCoverName()
        );

        return new BookCartResponse(bookCartShell, "Книга добавлена в корзину", "SUCCESS");
    }

    public BookCartResponse addOneBook(String userHash, String bookHash) {
        UserAndBook checkAnswer = this.checkUserAndBookExists(userHash, bookHash);
        if (!checkAnswer.getBookCartResponse().getVerdict().equals("SUCCESS"))
            return checkAnswer.getBookCartResponse();

        Optional<Cart> cartExist = cartRepository.findByUserIdAndBookId(Long.parseLong(userHash), Long.parseLong(bookHash));
        if (cartExist.isEmpty())
            return new BookCartResponse(null, "Книги нет в корзине", "BOOK_NOT_IN_CART");

        if (checkAnswer.getBook().getStorageAmount() == null)
            return new BookCartResponse(null, "Книга электронная. Больше добавить нельзя", "BOOK_IS_DIGITAL");

        if (checkAnswer.getBook().getStorageAmount() < cartExist.get().getAmount() + 1)
            return new BookCartResponse(null, "На складе нет больше книг", "STORAGE_DONT_HAVE_THIS_AMOUNT_BOOK");

        Cart cartUpdated;
        try {
            cartExist.get().setAmount(cartExist.get().getAmount() + 1);
            cartUpdated = cartRepository.save(cartExist.get());
        } catch (Exception e) {
            return new BookCartResponse(null, "Ошибка сохранения", "ERROR_SAVE");
        }

        BookCartShell bookCartShell = new BookCartShell(
                checkAnswer.getBook().getId().toString(),
                checkAnswer.getBook().getName(),
                checkAnswer.getBook().getAuthors(),
                cartUpdated.getAmount(),
                checkAnswer.getBook().getPrice(),
                checkAnswer.getBook().getCoverName()
        );

        return new BookCartResponse(bookCartShell, "Книга добавлена в корзину", "SUCCESS");
    }

    public BookCartResponse removeOneBook(String userHash, String bookHash) {
        UserAndBook checkAnswer = this.checkUserAndBookExists(userHash, bookHash);
        if (!checkAnswer.getBookCartResponse().getVerdict().equals("SUCCESS")) return checkAnswer.getBookCartResponse();

        Optional<Cart> cartExist = cartRepository.findByUserIdAndBookId(Long.parseLong(userHash), Long.parseLong(bookHash));
        if (cartExist.isEmpty()) return new BookCartResponse(null, "Книги нет в корзине", "BOOK_NOT_IN_CART");

        if (checkAnswer.getBook().getStorageAmount() == null) return new BookCartResponse(null, "Книга электронная", "BOOK_IS_DIGITAL");

        if (cartExist.get().getAmount() < 1)
            return new BookCartResponse(null, "Книг не может быть меньше нуля", "LESS_THEN_ZERO_BOOKS");

        Cart cartUpdated;
        try {
            cartExist.get().setAmount(cartExist.get().getAmount() - 1);
            cartUpdated = cartRepository.save(cartExist.get());
        } catch (Exception e) {
            return new BookCartResponse(null, "Ошибка сохранения", "ERROR_SAVE");
        }

        BookCartShell bookCartShell = new BookCartShell(
                checkAnswer.getBook().getId().toString(),
                checkAnswer.getBook().getName(),
                checkAnswer.getBook().getAuthors(),
                cartUpdated.getAmount(),
                checkAnswer.getBook().getPrice(),
                checkAnswer.getBook().getCoverName()
        );

        return new BookCartResponse(bookCartShell, "Книга убрана из корзины", "SUCCESS");
    }

    public BookCartResponse removeBook(String userHash, String bookHash) {
        UserAndBook checkAnswer = this.checkUserAndBookExists(userHash, bookHash);
        if (!checkAnswer.getBookCartResponse().getVerdict().equals("SUCCESS")) return checkAnswer.getBookCartResponse();

        Optional<Cart> cartExist = cartRepository.findByUserIdAndBookId(Long.parseLong(userHash), Long.parseLong(bookHash));
        if (cartExist.isEmpty()) return new BookCartResponse(null, "Книги нет в корзине", "BOOK_NOT_IN_CART");

        try {
            cartRepository.delete(cartExist.get());
        } catch (Exception e) {
            return new BookCartResponse(null, "Ошибка сохранения", "ERROR_SAVE");
        }

        return new BookCartResponse(null, "Книга удалена из корзины", "SUCCESS");
    }
}
