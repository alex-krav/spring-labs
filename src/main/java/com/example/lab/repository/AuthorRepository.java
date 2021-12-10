package com.example.lab.repository;

import com.example.lab.model.Author;

public interface AuthorRepository {
    Author findByName(String name);
    void deleteForBook(int id);
}
