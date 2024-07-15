package org.example.litland.repository;

import org.example.litland.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findRatingByUserIdAndBookId(Long userId, Long bookId);

    @Query(value = "select avg(r.\"mark\") from \"ratings\" r where r.\"book_id\"=:bookId", nativeQuery = true)
    Float averageRatingByBookId(@Param("bookId") Long bookId);

    Long countByBookId(Long bookId);
}
