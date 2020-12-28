package com.github.gabrielffguimaraes.libraryapi.exception;

import com.github.gabrielffguimaraes.libraryapi.model.entity.Book;

public class BusinessException extends RuntimeException {
    public BusinessException(String s) {
        super(s);
    }
}
