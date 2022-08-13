package org.library.libraryapi.service.imp;

import org.library.libraryapi.model.entity.Book;
import org.library.libraryapi.repository.BookRepository;
import org.library.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return this.bookRepository.save(book);
    }
}
