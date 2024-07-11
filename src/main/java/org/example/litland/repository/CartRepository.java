package org.example.litland.repository;

import org.example.litland.model.Cart;
import org.example.litland.model.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    List<Cart> findByUserIdAndStatus(Long userId, CartStatus status);
    Optional<Cart> findByUserIdAndBookIdAndStatus(Long userId, Long bookId, CartStatus status);
    Optional<Cart> findByBookIdAndUserId(Long bookId, Long userId);
}
