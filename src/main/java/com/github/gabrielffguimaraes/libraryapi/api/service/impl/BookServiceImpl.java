package com.github.gabrielffguimaraes.libraryapi.api.service.impl;

import com.github.gabrielffguimaraes.libraryapi.api.model.entity.Book;
import com.github.gabrielffguimaraes.libraryapi.api.model.repository.BookRepository;
import com.github.gabrielffguimaraes.libraryapi.api.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
