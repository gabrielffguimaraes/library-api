package org.library.libraryapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.libraryapi.exception.base.BookNotFoundException;
import org.library.libraryapi.exception.base.BusinessException;
import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;
import org.library.libraryapi.repository.BookRepository;
import org.library.libraryapi.service.imp.BookServiceImp;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.library.libraryapi.factory.BookFactory.createBook;
import static org.library.libraryapi.factory.BookFactory.createBookDto;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {
    @MockBean
    BookService service;
    @MockBean
    BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        this.service = new BookServiceImp( bookRepository );
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
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        Book savedBook = service.save(book1);
        System.out.println(savedBook);
        // verificacao
        Assertions.assertNotNull(savedBook.getId());
        assertEquals(savedBook.getIsbn(),"123");
        assertEquals(savedBook.getAuthor(),"Fulano");
        assertEquals(savedBook.getTitle(),"As aventuras");
    }

    @Test
    @DisplayName("Deve lançar um erro ao tentar salvar Isbn já cadastrado")
    public void shouldNotSaveWithDuplicatedIsbn () {
        // cenário
        final String MENSAGEM_ERRO = "Isbn já cadastrado !";
        BookDTO bookDTO = createBookDto();
        Mockito.when(this.bookRepository.existsByIsbn(Mockito.any(String.class))).thenReturn(true);
        // if test fails and not passing , will return saved value;
        Mockito.when(this.bookRepository.save(Mockito.any(Book.class))).thenReturn(createBook());

        // execução
        var ex  = Assertions.assertThrows(BusinessException.class , () -> {
            this.service.save(bookDTO);
        });

        // validação
        Assertions.assertAll(
                () -> assertInstanceOf(BusinessException.class,ex),
                () -> assertEquals(ex.getMessage(),MENSAGEM_ERRO)
        );
        Mockito.verify(bookRepository, Mockito.never()).save(Mockito.any(Book.class));
    }

    @Test
    @DisplayName("Deve atualizar um livro com sucesso .")
    public void shouldUpdateAbookSucessful() {
        // cenário
        final String AUTHOR = "Machado de Assis";
        BookDTO bookToUpdate = BookDTO
                .builder()
                .id(1l)
                .title("Crepusculo saga eclipe lunar")
                .author(AUTHOR)
                .isbn("Isbn 123 serie 5")
                .build();
        Mockito.when(this.bookRepository.save(Mockito.any(Book.class))).thenReturn(Book
                                                                                    .builder()
                                                                                    .id(1l)
                                                                                    .title("Crepusculo saga eclipe lunar")
                                                                                    .author(AUTHOR)
                                                                                    .isbn("Isbn 123 serie 5")
                                                                                    .build());
        // execução
        Book savedBook = this.service.save(bookToUpdate);
        // validaçao
        Assertions.assertNotNull(savedBook.getId());
        Assertions.assertEquals(savedBook.getAuthor(),bookToUpdate.getAuthor());
        Assertions.assertEquals(savedBook.getTitle(),bookToUpdate.getTitle());
        Assertions.assertEquals(savedBook.getIsbn(),bookToUpdate.getIsbn());
    }
    @Test
    @DisplayName("Deve lançar um BookNotFoundException ao tentar atualizar um livro na base de dados")
    public void shouldThrowsBookNotFoundExceptionOnUpdate() {
        // cenário
        final Long ID = 1l;
        BookDTO bookDTO = BookDTO
                .builder()
                .id(ID)
                .build();
        Mockito.when(this.bookRepository.findById(Mockito.anyLong())).thenThrow(new BookNotFoundException(ID));
        // execução
        BookNotFoundException ex = Assertions.assertThrows(BookNotFoundException.class, () -> {
            this.service.update(bookDTO,ID);
        });
        // testes
        Mockito.verify(this.bookRepository,Mockito.never()).save(Mockito.any(Book.class));
        Assertions.assertEquals(ex.getMessage() , "Livro não encontrado com o id :" + ID);
    }
}
