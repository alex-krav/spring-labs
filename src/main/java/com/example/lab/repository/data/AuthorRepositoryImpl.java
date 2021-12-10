package com.example.lab.repository.data;

import com.example.lab.model.Author;
import com.example.lab.repository.AuthorRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

@Profile("data")
public interface AuthorRepositoryImpl extends AuthorRepository, Repository<Author, Integer> {

    @Override
    @Query("SELECT id, fullname FROM author WHERE fullname = :name")
    Author findByName(@Param("name") String name);

    @Override
    @Query("DELETE from author_books WHERE book_id = :id")
    void deleteForBook(@Param("id") int id);
}
