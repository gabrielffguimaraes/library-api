package org.library.libraryapi.controller;

import org.library.libraryapi.model.entity.Book;
import org.library.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book));
    }
}
