package org.library.libraryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.libraryapi.exception.base.BookNotFoundException;
import org.library.libraryapi.exception.base.BusinessException;
import org.library.libraryapi.factory.BookFactory;
import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;
import org.library.libraryapi.service.BookService;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    BookService service;
    static String BOOK_API = "/api/v1/books";

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

        BDDMockito.given(service.save(Mockito.any(BookDTO.class))).willReturn(savedBook);
        String json = new ObjectMapper().writeValueAsString(bookDTO);

        var request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("author").value(bookDTO.getAuthor()))
                .andExpect(jsonPath("isbn").value(bookDTO.getIsbn()))
        ;
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficientes para criação do livro")
    public void createInvalidBookTest() throws Exception {
        var bookDto = BookDTO.builder()
                .title("Book 1")
                .build();

        var json = new ObjectMapper().writeValueAsString(bookDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        Mockito.when(service.save(Mockito.any(BookDTO.class))).thenReturn(new Book());
        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(2)));
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn já utilizado por outro")
    public void createBookWithDuplicatedIsbn() throws Exception {
        var bookDto = BookFactory.createNewBook();
        var json = new ObjectMapper().writeValueAsString(bookDto);
        final String MENSAGEM_DE_ERRO = "Isbn já cadastrado";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        Mockito.when(service.save(Mockito.any(BookDTO.class))).thenThrow(new BusinessException(MENSAGEM_DE_ERRO));

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(MENSAGEM_DE_ERRO));
    }

    @Test
    @DisplayName("Buscar informaçoes do livro salvo")
    public void getBookDetails() throws Exception {
        var bookDto = BookFactory.createBookDto();

        Mockito.when(service.findById(bookDto.getId())).thenReturn(bookDto);

        var request = MockMvcRequestBuilders
                .get(BOOK_API+"/"+bookDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);


        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(bookDto.getTitle()))
                .andExpect(jsonPath("isbn").value(bookDto.getIsbn()))
                .andExpect(jsonPath("author").value(bookDto.getAuthor()));

    }
    @Test
    @DisplayName("Deve lançar exceçao livro nao encontrado")
    public void shouldThrowBookNotFoundException() throws Exception {
        final Long ID = 10l;
        Mockito.when(service.findById(Mockito.anyLong())).thenThrow(new BookNotFoundException(ID));

        var request = MockMvcRequestBuilders
                .get(BOOK_API+"/"+ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errors[0]").value(Matchers.containsString("Livro não encontrado com o id")));
    }

   @Test
   @DisplayName("Deve deletar um livro com sucesso")
   public void shouldDeleteBook() throws Exception {
        final Long ID = 1l;
        Mockito.doNothing().when(service).deleteById(Mockito.anyLong());
        var request = MockMvcRequestBuilders.delete(BOOK_API+"/"+ID);
        mvc.perform(request)
                .andExpect(status().isNoContent());
   }

    @Test
    @DisplayName("Deve disparar exceçao de BookNotFoundException")
    public void shouldDispatchBookNotFoundExceptionOnDeletion() throws Exception {
        final Long ID = 1l;
        Mockito.doThrow(new BookNotFoundException(ID)).when(service).deleteById(ID);

        var request = MockMvcRequestBuilders.delete(BOOK_API+"/"+ID);
        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar um livro com sucesso")
    public void shouldUpdateBook() throws Exception {
        // cenário
        BookDTO bookDTO = BookFactory.createBookDto();

        BookDTO bookToUpdate = BookFactory.createBookDto();
        bookToUpdate.setAuthor("Teste");
        bookToUpdate.setIsbn("isbn 12345678");

        Mockito.when(this.service.update(Mockito.any(BookDTO.class),Mockito.anyLong())).thenReturn(bookToUpdate);

        var json = new ObjectMapper().writeValueAsString(bookDTO);
        var request = MockMvcRequestBuilders
                .put(BOOK_API+"/"+bookDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        // execuçao & testes
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(bookDTO.getId()))
                .andExpect(jsonPath("author").value(bookToUpdate.getAuthor()))
                .andExpect(jsonPath("isbn").value(bookToUpdate.getIsbn()));
    }
}
