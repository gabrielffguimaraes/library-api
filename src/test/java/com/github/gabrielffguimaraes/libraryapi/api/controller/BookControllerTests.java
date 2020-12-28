package com.github.gabrielffguimaraes.libraryapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gabrielffguimaraes.libraryapi.api.dto.BookDto;
import com.github.gabrielffguimaraes.libraryapi.model.entity.Book;
import com.github.gabrielffguimaraes.libraryapi.service.BookService;
import com.github.gabrielffguimaraes.libraryapi.exception.BusinessException;
import lombok.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)  old versions junit
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTests {
    static String BOOK_API = "/api/books";

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("deve criar um livro com sucesso")
    public void createBookTest() throws Exception{
        // ambiente
        var savedBook = new Book()
                .builder()
                .id(1L)
                .title("As aventuras de merlin")
                .author("Author")
                .isbn("123456").build();
        BDDMockito.given(bookService.save(Mockito.any(Book.class))).willReturn(savedBook);

        var bookDto = this.createNewBook();

        String json = new ObjectMapper().writeValueAsString(bookDto);
        var request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        // execução
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("title").value(bookDto.getTitle()))
                .andExpect(jsonPath("author").value(bookDto.getAuthor()))
                .andExpect(jsonPath("isbn").value(bookDto.getIsbn()));
        // teste
    }

    @Test
    @DisplayName("deve lançar um erro de validação ao criar um livro")
    public void createInvalidBookTest() throws Exception {
        // cenário
        String json = new ObjectMapper().writeValueAsString(new BookDto());
        var req = MockMvcRequestBuilders
                .post(BOOK_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // execução e teste
        mvc.perform(req)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors").exists());
    }

    @Test
    @DisplayName("deve lançar erro ao criar um livro com isbn duplicado")
    public void createBookWithDuplicatedIsbn() throws Exception {
        // cenário
        var bookDto = createNewBook();
        String json = new ObjectMapper().writeValueAsString(bookDto);
        String erroMsg = "Erro de isbn duplicado";
        BDDMockito.given(bookService.save(Mockito.any(Book.class))).willThrow(new BusinessException(erroMsg));
        var req = MockMvcRequestBuilders.
                post(BOOK_API).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                content(json);
        // execução
        mvc.perform(req)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("errors[0]").value(erroMsg));
        // teste
    }
    private BookDto createNewBook(){
        return new BookDto()
                .builder()
                .title("As aventuras de merlin")
                .author("Author")
                .isbn("123456").build();
    }
}
