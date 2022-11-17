package org.library.libraryapi.service;

import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;


public interface BookService {
    public Book save(BookDTO book);
}
