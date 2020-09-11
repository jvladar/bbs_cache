package com.javatechie.spring.ajax.api.dto;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Long> {
 //ulozenie vsetkych dát do tabulky
}
