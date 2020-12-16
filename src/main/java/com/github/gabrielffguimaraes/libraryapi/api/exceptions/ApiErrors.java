package com.github.gabrielffguimaraes.libraryapi.api.exceptions;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class ApiErrors {
    private List<String> err;
    public ApiErrors(BindingResult bindingResult) {
        this.err = new ArrayList<>();
        bindingResult.getAllErrors().forEach( error -> {
            this.err.add(error.getDefaultMessage());
        });
    }
    public List<String> getErrors(){
        return err;
    }
}
