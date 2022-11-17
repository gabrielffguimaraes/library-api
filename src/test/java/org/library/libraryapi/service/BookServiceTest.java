package org.library.libraryapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;
import org.library.libraryapi.repository.BookRepository;
import org.library.libraryapi.service.imp.BookServiceImp;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {
    @MockBean
    BookService service;
    @MockBean
    BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        //this.service = new BookServiceImp( bookRepository );
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest() {
        // cenario
        Book book = new Book();
        book.setIsbn("123");
        book.setAuthor("Fulano");
        book.setTitle("As aventuras");

        //Mockito.when()

        // execução
        BookDTO book1 = new BookDTO();
        book.setId(1l);
        book.setIsbn("123");
        book.setAuthor("Fulano");
        book.setTitle("As aventuras");
        Mockito.when(service.save(Mockito.any(BookDTO.class))).thenReturn(book);

        Book savedBook = service.save(book1);
        System.out.println(savedBook);
        // verificacao
        Assertions.assertNotNull(savedBook.getId());
        Assertions.assertEquals(savedBook.getIsbn(),"123");
        Assertions.assertEquals(savedBook.getAuthor(),"Fulano");
        Assertions.assertEquals(savedBook.getTitle(),"As aventuras");
    }
}
