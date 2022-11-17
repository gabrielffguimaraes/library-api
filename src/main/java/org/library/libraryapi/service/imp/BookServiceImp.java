package org.library.libraryapi.service.imp;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;
import org.library.libraryapi.repository.BookRepository;
import org.library.libraryapi.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(BookDTO dto) {
        Book book = new Book();
        BeanUtils.copyProperties(book,dto);
        return this.bookRepository.save(book);
    }
}
