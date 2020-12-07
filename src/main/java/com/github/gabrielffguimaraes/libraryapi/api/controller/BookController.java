package com.github.gabrielffguimaraes.libraryapi.api.controller;

import com.github.gabrielffguimaraes.libraryapi.api.dto.BookDto;
import com.github.gabrielffguimaraes.libraryapi.api.model.entity.Book;
import com.github.gabrielffguimaraes.libraryapi.api.service.BookService;
import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody BookDto bookDto){
        Book book = Book
                        .builder()
                            .title(bookDto.getTitle())
                            .author(bookDto.getAuthor())
                            .isbn(bookDto.getIsbn())
                            .build();
        book = bookService.save(book);
        return new BookDto()
                .builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .build();
    }


}
