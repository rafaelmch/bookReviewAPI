package com.bookReview.bookReview.repository;

import com.bookReview.bookReview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByComment(String comment);

}
