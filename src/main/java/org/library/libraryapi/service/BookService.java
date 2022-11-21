package org.library.libraryapi.service;

import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;


public interface BookService {
    Book save(BookDTO book);

    BookDTO findById(Long id);

    void deleteById(Long anyLong);
}
