package org.library.libraryapi.service;

import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface BookService {
    Book save(BookDTO book);

    BookDTO findById(Long id);

    void deleteById(Long anyLong);

    BookDTO update(BookDTO bookDTO, Long id);

    List<Book> findAll();

    void updateBookCover(MultipartFile multipartFile,Long id);
};
