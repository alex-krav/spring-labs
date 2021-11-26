package com.example.lab.repository;

import com.example.lab.model.Book;
import com.example.lab.model.Keyword;

public interface KeywordRepository {
    Keyword findByName(String name);
    void deleteForBook(Book book);
}
