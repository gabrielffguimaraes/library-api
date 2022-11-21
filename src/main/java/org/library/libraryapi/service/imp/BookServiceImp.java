package org.library.libraryapi.service.imp;

import org.library.libraryapi.exception.base.BookNotFoundException;
import org.library.libraryapi.exception.base.BusinessException;
import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;
import org.library.libraryapi.repository.BookRepository;
import org.library.libraryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save (BookDTO dto) {
        Book book = new Book();
        BeanUtils.copyProperties(dto,book);

        boolean exists = this.bookRepository.existsByIsbn(dto.getIsbn());
        if (exists) { throw new BusinessException("Isbn já cadastrado !"); };
        return this.bookRepository.save(book);
    }

    public BookDTO findById(Long id) {
        var book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        var bookDto = new ModelMapper().map(book,BookDTO.class);
        return bookDto;
    }

    public void deleteById(Long id) {
        bookRepository.findById(id)
                .ifPresentOrElse((book) -> {
                    this.bookRepository.deleteById(id);
                }, () -> new BookNotFoundException(id));
    }
}
