package com.github.gabrielffguimaraes.libraryapi.api.controller.service;

import com.github.gabrielffguimaraes.libraryapi.exception.BusinessException;
import com.github.gabrielffguimaraes.libraryapi.model.entity.Book;
import com.github.gabrielffguimaraes.libraryapi.model.repository.BookRepository;
import com.github.gabrielffguimaraes.libraryapi.service.BookService;
import com.github.gabrielffguimaraes.libraryapi.service.impl.BookServiceImpl;
import net.bytebuddy.implementation.bytecode.Throw;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
        Book book = this.createValidBook();
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(false);
        Book bookRetorno =  this.createValidBook();
        bookRetorno.setId(10l);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(bookRetorno);
        //BDDMockito.given(bookService.save(Mockito.any(Book.class))).willReturn(bookRetorno);

        // execução
        Book savedBook = bookService.save(book);

        // verificação
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isNotNull();
        Assertions.assertThat(savedBook.getIsbn()).isEqualTo(book.getIsbn());
        Assertions.assertThat(savedBook.getTitle()).isEqualTo(book.getTitle());
        Assertions.assertThat(savedBook.getAuthor()).isEqualTo(book.getAuthor());
    }
    @Test
    @DisplayName("Deve lançar um erro de negócio ao tentar salvar um livro com isbn duplicado")
    public void naoDeveSalvarIsbnDuplicado() {
        //cenário
        Book book = this.createValidBook();
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(true);
        //execução
        Throwable exception = Assertions.catchThrowable(() -> bookService.save(book));
        Assertions.assertThat(exception).isInstanceOf(BusinessException.class)
                .hasMessage("Erro de isbn duplicado");
        Mockito.verify(bookRepository,Mockito.never()).save(book);
    }
    private Book createValidBook() {
        return  Book.builder()
                .isbn("123456")
                .title("as aventuras do teste")
                .author("fulano").build();
    }
    
}
