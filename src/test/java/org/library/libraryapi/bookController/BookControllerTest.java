package org.library.libraryapi.bookController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {


    @Autowired
    MockMvc mvc;
    static String BOOK_API = "/api/books";

    @Test
    @DisplayName("Deve criar um livro com sucesso .")
    public void createBookTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(null);
        var request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
        ;
    }
}
