package org.library.libraryapi.controller;

import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;
import org.library.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody @Valid BookDTO book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book));
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

}
