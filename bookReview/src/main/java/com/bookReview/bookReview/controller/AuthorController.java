package com.bookReview.bookReview.controller;

import com.bookReview.bookReview.model.Author;
import com.bookReview.bookReview.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    /** List all authors, paged (default size = 10) */
    @GetMapping
    public Page<Author> listAll(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return authorService.list(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getOne(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author create(@Valid @RequestBody Author payload) {
        return authorService.create(payload);
    }

    @GetMapping("/all")
    public List<Author> listAll() {
        return authorService.listAll();
    }


}
