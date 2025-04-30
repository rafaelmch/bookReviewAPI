package com.bookReview.bookReview.repository;

import com.bookReview.bookReview.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
