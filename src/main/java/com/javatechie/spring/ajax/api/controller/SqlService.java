package com.javatechie.spring.ajax.api.controller;


import com.javatechie.spring.ajax.api.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class SqlService {

    @Autowired
    SqlService sqlService;

    @Autowired
    private JpaConfig jpaConfig;

    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getBookName(int bookid){
        String sql = "SELECT book_name FROM book where book_id = ?";
        //jdbcTemplate.setDataSource(jpaConfig.getDataSource());
        return jdbcTemplate.queryForObject(sql, new Object[]{bookid},String.class);
    }

    public List<String> getBookNames(){
        String sql = "SELECT DISTINCT book_name FROM book";
        //jdbcTemplate.setDataSource(jpaConfig.getDataSource());
        return jdbcTemplate.queryForList(sql,String.class);
    }

    public List<Book> getBooks(){
        String sql = "SELECT * FROM book";
        //jdbcTemplate.setDataSource(jpaConfig.getDataSource());
        return jdbcTemplate.query(sql,new BookMapper());
    }
    public List<Book> getBookSearch(Book book){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM book where book_name = '");
        sql.append(book.getBookName()+"'");
        String sqlS=sql.toString();
        //jdbcTemplate.setDataSource(jpaConfig.getDataSource());
        return jdbcTemplate.query(sqlS,new BookMapper());
    }


    private static final class BookMapper implements RowMapper<Book>{
        @Override
        public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException{
            Book book = new Book();
            book.setBookId(resultSet.getLong("book_id"));
            book.setAuthor(resultSet.getString("author"));
            book.setBookName(resultSet.getString("book_name"));
            return book;
        }
    }
}

