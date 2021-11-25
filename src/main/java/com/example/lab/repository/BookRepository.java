package com.example.lab.repository;

import com.example.lab.model.Book;

import java.util.Collection;

public interface BookRepository {
    Collection<Book> findAll();
    Collection<Book> findByName(String name);
    Collection<Book> findByAuthor(String author);
    Collection<Book> findByKeyword(String keyword);
    Book findById(int id);
    void save(Book book);
    void delete(Book book);
}
