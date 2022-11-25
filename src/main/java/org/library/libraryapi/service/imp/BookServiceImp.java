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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    @Override
    public BookDTO update(BookDTO bookDTO, Long id) {
        var book = this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        return null;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    public void updateBookCover(MultipartFile file,Long id) {
        Book book = this.bookRepository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException());

        book.setBookCover(file.getOriginalFilename());
        this.bookRepository.save(book);
        this.uploadBookCover(file);
    }
    private void uploadBookCover(MultipartFile file) {
        final String UPLOAD_FOLDER = "C://Users//gl-ki//OneDrive//Área de Trabalho//library-api//files//";
        try {
            // get file and save
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path,bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
