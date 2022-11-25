package org.library.libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;
import org.library.libraryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@CrossOrigin(origins = "*")
@Tag(description = "Book endpoints",name = "Book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Operaçao deve salvar um livro")
    @PostMapping
    public ResponseEntity<Book> save(@RequestBody @Valid BookDTO book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book));
    }

    @Operation(summary = "Operaçao deve listar todos os livros")
    @GetMapping
    public ResponseEntity<List<BookDTO>> findAll() {
        List<BookDTO> books = List.of(new ModelMapper().map(this.bookService.findAll(),BookDTO[].class));
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Operaçao deve resgatar um livro apartir do id")
    @GetMapping("{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(id));
    }


    @Operation(summary = "Operaçao deve deletar um livro apartir do id")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @Operation(summary = "Operaçao deve atualizar um livro apartir do id")
    @PutMapping("{id}")
    public ResponseEntity<BookDTO> update(@RequestBody @Valid BookDTO bookDTO,@PathVariable Long id) {
        return ResponseEntity.ok(this.bookService.update(bookDTO,id));
    }
    @Operation(summary = "Operation deve atualizar a capa do livro")
    @PatchMapping("/upload")
    public void uploadBookCover(@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam("id") Long id) {
        this.bookService.updateBookCover(file,id);
    }
}
