package org.library.libraryapi.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.libraryapi.controller.BookController;
import org.library.libraryapi.controller.BookControllerTest;
import org.library.libraryapi.factory.BookFactory;
import org.library.libraryapi.model.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    BookRepository bookRepository;
    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com isbn informado")
    public void returnTrueWhenIsbnExists() {
        var books = this.bookRepository.findAll();
        log.info("Books : {}",books);

        Book saved = this.bookRepository.save(BookFactory.createNewBook());
        boolean exists = bookRepository.existsByIsbn(saved.getIsbn());
        Assertions.assertThat(exists).isTrue();
    }
}

