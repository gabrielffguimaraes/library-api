package com.github.gabrielffguimaraes.libraryapi.service.impl;

import com.github.gabrielffguimaraes.libraryapi.exception.BusinessException;
import com.github.gabrielffguimaraes.libraryapi.model.entity.Book;
import com.github.gabrielffguimaraes.libraryapi.model.repository.BookRepository;
import com.github.gabrielffguimaraes.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        if( bookRepository.existsByIsbn(book.getIsbn()) ){
            throw new BusinessException("Erro de isbn duplicado");
        }
        return bookRepository.save(book);
    }
}
