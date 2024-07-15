package org.example.litland.service;

import org.example.litland.model.*;
import org.example.litland.repository.BookRepository;
import org.example.litland.repository.CartRepository;
import org.example.litland.repository.OrderRepository;
import org.example.litland.repository.UserRepository;
import org.example.litland.response.BookCartResponse;
import org.example.litland.response.OrderResponse;
import org.example.litland.shell.BookCartShell;
import org.example.litland.shell.BookShell;
import org.example.litland.shell.CartShell;
import org.example.litland.shell.UserAndBook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CartService(CartRepository cartRepository,
                       BookRepository bookRepository,
                       UserRepository userRepository,
                       OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    private Long decodeUserHash(String hash) {
        // TODO 3 - Сделать декодер для id пользователя
        return Long.parseLong(hash);
    }

    public List<BookCartShell> getAllByUser(String userHash) {
        List<BookCartShell> bookShells = new ArrayList<>();
        if (userHash.isBlank()) return bookShells;
        List<Cart> carts = cartRepository.findByUserIdAndStatus(this.decodeUserHash(userHash), CartStatus.IN_CART);
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

        Optional<Cart> cartExist = cartRepository.findByUserIdAndBookIdAndStatus(Long.parseLong(userHash), Long.parseLong(bookHash), CartStatus.IN_CART);
        if (cartExist.isPresent()) return new BookCartResponse(null, "Книга уже в корзине", "BOOK_IN_CART");

        if (checkAnswer.getBook().getStorageAmount() < 1)
            return new BookCartResponse(null, "На складе нет больше книг", "STORAGE_DONT_HAVE_THIS_AMOUNT_BOOK");

        Cart cartSaved;
        try {
            Cart cart = new Cart();
            cart.setBook(checkAnswer.getBook());
            cart.setDateAddingToCart(new Date());
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

        Optional<Cart> cartExist = cartRepository.findByUserIdAndBookIdAndStatus(Long.parseLong(userHash), Long.parseLong(bookHash), CartStatus.IN_CART);
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

        Optional<Cart> cartExist = cartRepository.findByUserIdAndBookIdAndStatus(Long.parseLong(userHash), Long.parseLong(bookHash), CartStatus.IN_CART);
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

        Optional<Cart> cartExist = cartRepository.findByUserIdAndBookIdAndStatus(Long.parseLong(userHash), Long.parseLong(bookHash), CartStatus.IN_CART);
        if (cartExist.isEmpty()) return new BookCartResponse(null, "Книги нет в корзине", "BOOK_NOT_IN_CART");

        try {
            cartRepository.delete(cartExist.get());
        } catch (Exception e) {
            return new BookCartResponse(null, "Ошибка сохранения", "ERROR_SAVE");
        }

        return new BookCartResponse(null, "Книга удалена из корзины", "SUCCESS");
    }

    public OrderResponse order(String userHash, List<BookCartShell> carts) {
        Date datePurchasing = new Date();
        Optional<User> user = userRepository.findById(Long.parseLong(userHash));
        if (user.isEmpty()) {
            return new OrderResponse(null, new ArrayList<>(), "USER_NOT_FOUND", "Пользователь не найден");
        }

        List<Cart> cartList = new ArrayList<>();

        for (BookCartShell bookCartShellShell : carts) {
            Optional<Book> bookBD = bookRepository.findById(Long.parseLong(bookCartShellShell.getHash()));
            if (bookBD.isEmpty()) return new OrderResponse(null, new ArrayList<>(), "BOOK_NOT_FOUND", "Книги уже нет на складе");

            Optional<Cart> cartBD = cartRepository.findByUserIdAndBookIdAndStatus(user.get().getId(), bookBD.get().getId(), CartStatus.IN_CART);
            if (cartBD.isEmpty()) return new OrderResponse(null, new ArrayList<>(), "CART_NOT_FOUND", "Книги нет в корзине");

            if (cartBD.get().getBook().getStorageAmount() != null) {
                if (cartBD.get().getAmount() > cartBD.get().getBook().getStorageAmount()) {
                    return new OrderResponse(null, new ArrayList<>(), "STORAGE_AMOUNT",
                            "На складе не осталость " + cartBD.get().getBook().getStorageAmount() + " книг: " + cartBD.get().getBook().getName());
                }
            }
            cartList.add(cartBD.get());
        }

        Order order = new Order();
        order.setCarts(cartList);
        order.setUser(user.get());
        order = orderRepository.save(order);

        List<CartShell> cartShells = new ArrayList<>();

        for (Cart cart : cartList) {
            if (cart.getBook().getStorageAmount() != null) {
                cart.getBook().setStorageAmount(cart.getBook().getStorageAmount() - cart.getAmount());
            }
            cart.setStatus(CartStatus.PURCHASED);
            cart.setDatePurchasing(datePurchasing);
            cart.setOrder(order);
            cartRepository.save(cart);

            CartShell cartShell = new CartShell(
                    cart.getBook().getId().toString(),
                    cart.getBook().getName(),
                    cart.getBook().getAuthors(),
                    cart.getAmount(),
                    cart.getBook().getPrice(),
                    cart.getBook().getCoverName(),
                    cart.getStatus()
            );
            cartShells.add(cartShell);
        }

        return new OrderResponse(datePurchasing, cartShells, "SUCCESS", "");
    }
}
