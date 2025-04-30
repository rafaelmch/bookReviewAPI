package com.bookReview.bookReview.service;

import com.bookReview.bookReview.model.Author;
import com.bookReview.bookReview.model.Book;
import com.bookReview.bookReview.model.Review;
import com.bookReview.bookReview.repository.AuthorRepository;
import com.bookReview.bookReview.repository.BookRepository;
import com.bookReview.bookReview.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ReviewRepository reviewRepository;

    public Book create(Book input){

        // Validate author exists
        Long authorId = input.getAuthor().getId();
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author doesn't exist: " + authorId));

        Book book = new Book();
        book.setTitle(input.getTitle());
        book.setDescription(input.getDescription());
        book.setPublishedDate(input.getPublishedDate());
        book.setAuthor(author);
        Book saved = bookRepository.save(book);

        List<Review> reviews = input.getReview().stream()
                .map(rp -> {
                    Review rev = new Review();
                    rev.setRating(rp.getRating());
                    rev.setComment(rp.getComment());
                    rev.setBook(saved);
                    return reviewRepository.save(rev);
                })
                .collect(Collectors.toList());

        saved.setReview(reviews);
        return saved;
    }

    // update book
    @Transactional
    public Book update(Long id, Book input) {
        // 1️⃣ Load existing book
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));

        // 2️⃣ Validate new author if provided
        Long newAuthorId = input.getAuthor().getId();
        Author author = authorRepository.findById(newAuthorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found: " + newAuthorId));
        existing.setAuthor(author);

        // 3️⃣ Update basic fields
        existing.setTitle(input.getTitle());
        existing.setDescription(input.getDescription());
        existing.setPublishedDate(input.getPublishedDate());

        // 4️⃣ Replace reviews:
        //    a) delete old ones
        reviewRepository.deleteAll(existing.getReview());
        existing.getReview().clear();

        //    b) create & save new ones linked to existing book
        List<Review> reviews = input.getReview().stream()
                .map(rp -> {
                    Review r = new Review();
                    r.setRating(rp.getRating());
                    r.setComment(rp.getComment());
                    r.setBook(existing);
                    return reviewRepository.save(r);
                })
                .collect(Collectors.toList());

        existing.setReview(reviews);

        // 5️⃣ Return updated
        return existing;
    }

    public Book getById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public void delete(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
        bookRepository.deleteById(id);
    }

    /** List all books, optionally filtered by author */
    public Page<Book> listAll(Long authorId, Pageable pageable) {
        if (authorId != null) {
            return bookRepository.findByAuthorId(authorId, pageable);
        }
        return bookRepository.findAll(pageable);
    }

}
