package com.github.gabrielffguimaraes.libraryapi.api.controller.service;

import com.github.gabrielffguimaraes.libraryapi.api.model.entity.Book;
import com.github.gabrielffguimaraes.libraryapi.api.model.repository.BookRepository;
import com.github.gabrielffguimaraes.libraryapi.api.service.BookService;
import com.github.gabrielffguimaraes.libraryapi.api.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {
    @MockBean
    BookRepository bookRepository;
    BookService bookService;

    @BeforeEach
    public void setUp(){
        this.bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve salvar o livro")
    public void saveBookTest(){
        // cenário
        Book book = Book.builder()
                .isbn("123456")
                .title("as aventuras do teste")
                .author("fulano").build();

        Book bookRetorno = Book.builder()
                .id(1l)
                .isbn("123456")
                .title("as aventuras do teste")
                .author("fulano").build();
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(bookRetorno);
        //BDDMockito.given(bookService.save(Mockito.any(Book.class))).willReturn(bookRetorno);

        // execução
        Book savedBook = bookService.save(book);

        // verificação
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isEqualTo(1l);
        Assertions.assertThat(savedBook.getIsbn()).isEqualTo(book.getIsbn());
        Assertions.assertThat(savedBook.getTitle()).isEqualTo(book.getTitle());
        Assertions.assertThat(savedBook.getAuthor()).isEqualTo(book.getAuthor());
    }
    
}
