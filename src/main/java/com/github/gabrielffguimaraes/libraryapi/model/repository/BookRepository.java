package com.github.gabrielffguimaraes.libraryapi.model.repository;

import com.github.gabrielffguimaraes.libraryapi.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {
    public Book save(Book book);

    boolean existsByIsbn(String isbn);
}
