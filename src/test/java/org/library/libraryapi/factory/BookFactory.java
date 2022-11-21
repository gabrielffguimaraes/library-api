package org.library.libraryapi.factory;

import org.library.libraryapi.model.dto.BookDTO;
import org.library.libraryapi.model.entity.Book;

public class BookFactory {
    public static BookDTO createNewBookDto() {
        return  BookDTO.builder()
                .title("Book 1")
                .isbn("123")
                .author("Fulano de tal")
                .build();
    }
    public static Book createNewBook() {
        return  Book.builder()
                .title("Book 1")
                .isbn("123")
                .author("Fulano de tal")
                .build();
    }
    public static BookDTO createBookDto() {
        return  BookDTO.builder()
                .id(1l)
                .title("Book 1")
                .isbn("123")
                .author("Fulano de tal")
                .build();
    }
    public static Book createBook() {
        return  Book.builder()
                .id(1l)
                .title("Book 1")
                .isbn("123")
                .author("Fulano de tal")
                .build();
    }
}
