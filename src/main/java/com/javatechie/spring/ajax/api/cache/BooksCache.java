package com.javatechie.spring.ajax.api.cache;

import com.javatechie.spring.ajax.api.controller.SqlService;
import com.javatechie.spring.ajax.api.dto.Book;
import com.javatechie.spring.ajax.api.dto.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BooksCache {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    SqlService sqlService;

    @Cacheable(value = "booksCache")
    public List<Book> getUsers() {
        System.out.println("Nacitane knihy");
        return sqlService.getBooks();
    }
    @Cacheable(value = "booksNameCache")
    public List<String> getNames() {
        System.out.println("Nacitane mena knih");
        return sqlService.getBookNames();
    }
}
