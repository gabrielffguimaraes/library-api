package org.library.libraryapi.exception.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ApiError {
    private  List<String> errors = new ArrayList<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(List<ObjectError> list) {
        for (ObjectError error : list) {
            errors.add(error.getDefaultMessage());
        }
    }
    public ApiError(BusinessException ex){
        this.errors = List.of(ex.getMessage());
    }

    public ApiError(BookNotFoundException ex){
        this.errors = List.of(ex.getMessage());
    }
}
