package com.github.gabrielffguimaraes.libraryapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApiApplication {

@Bean
public ModelMapper moddelMapper(){
	return new ModelMapper();
}

public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}

}
