package com.github.gabrielffguimaraes.libraryapi.api.exceptions;

import com.github.gabrielffguimaraes.libraryapi.exception.BusinessException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    private List<String> err;
    public ApiErrors(BindingResult bindingResult) {
        this.err = new ArrayList<>();
        bindingResult.getAllErrors().forEach( error -> {
            this.err.add(error.getDefaultMessage());
        });
    }
    public ApiErrors(BusinessException ex) {
        this.err = Arrays.asList(ex.getMessage());
    }
    public List<String> getErrors(){
        return err;
    }
}
