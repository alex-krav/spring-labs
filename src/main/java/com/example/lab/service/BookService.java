package com.example.lab.service;

import com.example.lab.model.Book;
import com.example.lab.model.Form;

import java.util.Collection;

public interface BookService {
    Collection<Book> findBooks();
    Collection<Book> findBooksByName(String name);
    Collection<Book> findBooksByAuthor(String author);
    Collection<Book> findBooksByKeyword(String keyword);
    Book findBookById(int id);
    void save(Book book);
    Book save(Form form);
    void delete(int id);
}
