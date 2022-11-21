package org.library.libraryapi.exception.base;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(Long id) {
        super("Livro não encontrado com o id :" + id);
    };

    public BookNotFoundException() {
        super("Livro não encontrado. ");
    };
}
