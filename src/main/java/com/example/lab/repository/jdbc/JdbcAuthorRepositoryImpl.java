package com.example.lab.repository.jdbc;

import com.example.lab.model.Author;
import com.example.lab.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Profile("jdbc")
@Repository
public class JdbcAuthorRepositoryImpl implements AuthorRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Author findByName(String name) {

        List<Author> authors = this.namedParameterJdbcTemplate.query(
                "SELECT id, fullname FROM author WHERE fullname = :fullname",
                Map.of("fullname", name),
                BeanPropertyRowMapper.newInstance(Author.class)
        );
        return authors.isEmpty() ? null : authors.get(0);
    }

    @Override
    public void deleteForBook(int id) {
        this.namedParameterJdbcTemplate.update(
                "DELETE from author_books WHERE book_id = :id",
                Map.of("id", id));
    }
}
