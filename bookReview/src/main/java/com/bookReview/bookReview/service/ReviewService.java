package com.bookReview.bookReview.service;

import com.bookReview.bookReview.model.Author;
import com.bookReview.bookReview.model.Book;
import com.bookReview.bookReview.model.Review;
import com.bookReview.bookReview.repository.BookRepository;
import com.bookReview.bookReview.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public Review create(Review review){
        // 1️⃣ grab the id from the incoming payload
        Long bookId = review.getBook().getId();
        // 2️⃣ load the actual Book (or 404)
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + bookId));
        // 3️⃣ attach the managed Book instance
        review.setBook(book);
        // 4️⃣ now save the Review with a fully initialized relationship
        return reviewRepository.save(review);
    }

    public Page<Review> list(Pageable pageable){
        return reviewRepository.findAll(pageable);
    }

    public Review getById(Long id){
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found: " + id));
    }

    @Transactional
    public Review update(Long id, Review payload) {
        Review existing = getById(id);
        existing.setRating(payload.getRating());
        existing.setComment(payload.getComment());
        return reviewRepository.save(existing);
    }

    @Transactional
    public void delete(Long id){
        reviewRepository.delete(getById(id));
    }

}
