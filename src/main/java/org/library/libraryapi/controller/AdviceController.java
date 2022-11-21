package org.library.libraryapi.controller;

import org.library.libraryapi.exception.base.ApiError;
import org.library.libraryapi.exception.base.BookNotFoundException;
import org.library.libraryapi.exception.base.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError manipularRegrasDeNegocioEx(MethodArgumentNotValidException ex) {
        return new ApiError(ex.getBindingResult().getAllErrors());
    }

    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError manipularRegrasDeNegocioEx(BusinessException ex) {
        return new ApiError(ex);
    }

    @ExceptionHandler(value = {BookNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError manipularNaoEncontradoEx(BookNotFoundException ex) {
        return new ApiError(ex);
    }
}
