package com.bookReview.bookReview.controller;

import com.bookReview.bookReview.model.Book;
import com.bookReview.bookReview.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Page<Book> list(
            @RequestParam(required = false) Long authorId,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return bookService.listAll(authorId, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getOne(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody Book payload) {
        return bookService.create(payload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @Valid @RequestBody Book payload){

        Book updated = bookService.update(id, payload);
        return ResponseEntity.ok(updated);

    }

    // delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

}
