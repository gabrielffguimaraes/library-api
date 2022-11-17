package org.library.libraryapi.exception.base;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ApiError {
    public String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss")
    public LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(String message) {
        this.message = message;
    }
}
