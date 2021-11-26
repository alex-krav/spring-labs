package com.example.lab.repository;

import com.example.lab.model.Author;
import com.example.lab.model.Book;

public interface AuthorRepository {
    Author findByName(String name);
    void deleteForBook(Book book);
}
