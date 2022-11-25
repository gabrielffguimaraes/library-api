package org.library.libraryapi.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.libraryapi.exception.base.BookNotFoundException;
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

    @Test
    @DisplayName("Deve Atualizar o livro com sucesso")
    public void shouldUpdateBookSucessful() {
        // context
        final String AUTHOR = "Machado de Assis";
        Book book = Book.builder()
                    .author(AUTHOR)
                    .title("The vampire diaries")
                    .isbn("Isbn serie 1")
                    .build();
        /* saving book to update further */
        Book saved = this.bookRepository.save(book);
        /* verifying if book exists */
        Book bookToUpdate = this.bookRepository.findById(saved.getId())
                .orElseThrow(() -> new BookNotFoundException(saved.getId()));

        /* changing */
        bookToUpdate.setTitle("Crepusculo");
        bookToUpdate.setIsbn("Isbn serie 2");

        /* saving book */
        Book bookUpdated = this.bookRepository.save(bookToUpdate);

        // Testing
        Assertions.assertThat(bookUpdated.getId()).isNotNull();
        Assertions.assertThat(bookUpdated.getIsbn()).isEqualTo("Isbn serie 2");
        Assertions.assertThat(bookUpdated.getTitle()).isEqualTo("Crepusculo");
        Assertions.assertThat(bookUpdated.getAuthor()).isEqualTo(AUTHOR);


    }
}

