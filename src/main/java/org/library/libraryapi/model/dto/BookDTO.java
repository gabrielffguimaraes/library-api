package org.library.libraryapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    Long id;

    @NotBlank(message = "Isbn obrigatório")
    String isbn;
    @NotBlank(message = "Author obrigatório")
    String author;
    @NotBlank(message = "Título do livro obrigatório")
    String title;
}
