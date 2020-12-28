package com.github.gabrielffguimaraes.libraryapi.api.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Long id;
    @NotEmpty(message = "Campo de titulo não pode estar vazio")
    private String title;
    @NotEmpty(message = "Campo de author não pode estar vazio")
    private String author;
    @NotEmpty(message = "Campo isbn não pode estar vazio")
    private String isbn;
}