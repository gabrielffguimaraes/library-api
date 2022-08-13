package org.library.libraryapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;
import org.library.libraryapi.service.BookService;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerTest {


    @Autowired
    MockMvc mvc;

    @MockBean
    BookService service;
    static String BOOK_API = "/api/books";

    @Test
    @DisplayName("Deve criar um livro com sucesso .")
    public void createBookTest() throws Exception {
        // cenário
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("Arthur");
        bookDTO.setTitle("As aventuras");
        bookDTO.setIsbn("001");

        Book savedBook = new Book();
        savedBook.setId(1l);
        savedBook.setAuthor("Arthur");
        savedBook.setTitle("As aventuras");
        savedBook.setIsbn("001");

        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(savedBook);
        String json = new ObjectMapper().writeValueAsString(bookDTO);

        var request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value(bookDTO.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value(bookDTO.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(bookDTO.getIsbn()))
        ;
    }
}
