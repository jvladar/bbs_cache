package com.javatechie.spring.ajax.api;

import com.javatechie.spring.ajax.api.cache.BooksCache;
import com.javatechie.spring.ajax.api.dto.Book;
import com.javatechie.spring.ajax.api.dto.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class SpringAjaxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAjaxApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(BookRepository repository, BooksCache booksCache){
		return args -> {
			repository.save(new Book("peler","jano"));
			repository.save(new Book("meno_knihyy","Jozef"));
			repository.save(new Book("pelerr","jano"));
			repository.save(new Book("meno_knihyyy","Jozef"));
			repository.save(new Book("pelerrr","jano"));
			repository.save(new Book("meno_knihyyyy","Jozef"));
			booksCache.getUsers();
			//booksCache.getNames();
		};
	}

}
