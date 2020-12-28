package com.github.gabrielffguimaraes.libraryapi.api.controller;

import com.github.gabrielffguimaraes.libraryapi.api.dto.BookDto;
import com.github.gabrielffguimaraes.libraryapi.api.exceptions.ApiErrors;
import com.github.gabrielffguimaraes.libraryapi.exception.BusinessException;
import com.github.gabrielffguimaraes.libraryapi.model.entity.Book;
import com.github.gabrielffguimaraes.libraryapi.service.BookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody @Valid BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        book = bookService.save(book);
        BookDto response = modelMapper.map(book, BookDto.class);
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerErrorsExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerBusinessExceptions(BusinessException ex ){
        return new ApiErrors(ex);
    }
}
