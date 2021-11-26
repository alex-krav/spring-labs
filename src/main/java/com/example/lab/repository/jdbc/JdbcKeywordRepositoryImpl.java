package com.example.lab.repository.jdbc;

import com.example.lab.model.Book;
import com.example.lab.model.Keyword;
import com.example.lab.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcKeywordRepositoryImpl implements KeywordRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Keyword findByName(String name) {

        List<Keyword> keywords = this.namedParameterJdbcTemplate.query(
                "SELECT id, name FROM keyword WHERE name = :name",
                Map.of("name", name),
                BeanPropertyRowMapper.newInstance(Keyword.class)
        );
        return keywords.isEmpty() ? null : keywords.get(0);
    }

    @Override
    public void deleteForBook(Book book) {
        this.namedParameterJdbcTemplate.update(
                "DELETE from book_keywords WHERE book_id = :id",
                Map.of("id", book.getId()));
    }
}
