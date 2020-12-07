package com.github.gabrielffguimaraes.libraryapi.api.model.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
}
