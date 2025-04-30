package com.bookReview.bookReview.service;

import com.bookReview.bookReview.model.Author;
import com.bookReview.bookReview.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Author create(Author author){
        return authorRepository.save(author);
    }

    @Transactional
    public Page<Author> list(Pageable pg){
        return authorRepository.findAll(pg);
    }

    @Transactional
    public Author getById(Long id){
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found: " + id));
    }

    @Transactional
    public Author update(Long id, Author payload) {
        Author existing = getById(id);
        existing.setName(payload.getName());
        existing.setBiography(payload.getBiography());
        return authorRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        authorRepository.delete(getById(id));
    }

    public List<Author> listAll() {
        return authorRepository.findAll();
    }

}
