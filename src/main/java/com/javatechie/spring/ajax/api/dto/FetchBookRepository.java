package com.javatechie.spring.ajax.api.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FetchBookRepository extends JpaRepository<Book,Long> {
    @Override
    List<Book> findAll();
    //nacitanie dat z tabulky
}
