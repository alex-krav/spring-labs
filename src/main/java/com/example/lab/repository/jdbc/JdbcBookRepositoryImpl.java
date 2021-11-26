package com.example.lab.repository.jdbc;

import com.example.lab.model.Author;
import com.example.lab.model.BaseEntity;
import com.example.lab.model.Book;
import com.example.lab.model.Keyword;
import com.example.lab.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcBookRepositoryImpl implements BookRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertBook;
    private SimpleJdbcInsert insertAuthor;
    private SimpleJdbcInsert insertAuthorBooks;
    private SimpleJdbcInsert insertKeyword;
    private SimpleJdbcInsert insertBookKeywords;

    @Autowired
    public JdbcBookRepositoryImpl(DataSource dataSource) {

        this.insertBook = new SimpleJdbcInsert(dataSource)
                .withTableName("book")
                .usingGeneratedKeyColumns("id");

        this.insertAuthor = new SimpleJdbcInsert(dataSource)
                .withTableName("author")
                .usingGeneratedKeyColumns("id");

        this.insertAuthorBooks = new SimpleJdbcInsert(dataSource)
                .withTableName("author_books");

        this.insertKeyword = new SimpleJdbcInsert(dataSource)
                .withTableName("keyword")
                .usingGeneratedKeyColumns("id");

        this.insertBookKeywords = new SimpleJdbcInsert(dataSource)
                .withTableName("book_keywords");
    }

    @Override
    public Collection<Book> findAll() {
        List<Book> books = this.namedParameterJdbcTemplate.query(
                "SELECT id, name FROM book order by name",
                BeanPropertyRowMapper.newInstance(Book.class)
        );
        loadBookAuthorsAndKeywords(books);
        return books;
    }

    @Override
    public Collection<Book> findByName(String name) {
        List<Book> books = this.namedParameterJdbcTemplate.query(
                "SELECT distinct(id), name FROM book WHERE UPPER(name) LIKE UPPER(:name) order by name",
                Map.of("name", "%" + name + "%"),
                BeanPropertyRowMapper.newInstance(Book.class)
        );
        loadBookAuthorsAndKeywords(books);
        return books;
    }

    @Override
    public Collection<Book> findByAuthor(String name) {
        List<Book> books = this.namedParameterJdbcTemplate.query(
                "SELECT distinct(b.id), b.name FROM author a LEFT JOIN author_books ab ON a.id = ab.author_id " +
                        "LEFT JOIN book b ON b.id = ab.book_id WHERE UPPER(a.fullname) LIKE UPPER(:fullname) order by b.name",
                Map.of("fullname", "%" + name + "%"),
                BeanPropertyRowMapper.newInstance(Book.class)
        );
        loadBookAuthorsAndKeywords(books);
        return books;
    }

    @Override
    public Collection<Book> findByKeyword(String name) {
        List<Book> books = this.namedParameterJdbcTemplate.query(
                "SELECT distinct(b.id), b.name FROM keyword k LEFT JOIN book_keywords bk ON k.id = bk.keyword_id " +
                        "LEFT JOIN book b ON b.id = bk.book_id WHERE UPPER(k.name) LIKE UPPER(:name) order by b.name",
                Map.of("name", "%" + name + "%"),
                BeanPropertyRowMapper.newInstance(Book.class)
        );
        loadBookAuthorsAndKeywords(books);
        return books;
    }

    @Override
    public Book findById(int id) {
        Book book;

        try {
            book = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT id, name FROM book WHERE id= :id",
                    Map.of("id", id),
                    BeanPropertyRowMapper.newInstance(Book.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Book.class, id);
        }

        if (book != null) {
            loadAuthors(book);
            loadKeywords(book);
        }

        return book;
    }

    @Override
    public void save(Book book) {

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(book);
        if (book.isNew()) {
            Number newKey = this.insertBook.executeAndReturnKey(parameterSource);
            book.setId(newKey.intValue());
            saveAuthors(book);
            saveKeywords(book);
        } else {
            this.namedParameterJdbcTemplate.update(
                    "UPDATE book SET name=:name WHERE id=:id",
                    parameterSource);
            saveAuthors(book);
            saveKeywords(book);
        }
    }

    @Override
    public void delete(Book book) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(book);
        this.namedParameterJdbcTemplate.update(
                "DELETE from book WHERE id=:id",
                parameterSource);
        this.namedParameterJdbcTemplate.update(
                "DELETE from author_books WHERE book_id = :id",
                Map.of("id", book.getId()));
        this.namedParameterJdbcTemplate.update(
                "DELETE from book_keywords WHERE book_id = :id",
                Map.of("id", book.getId()));

        deleteAuthorsWithoutBooks(book);
        deleteKeywordsWithoutBooks(book);
    }

    private void deleteAuthorsWithoutBooks(Book book) {
        for (Author author : book.getAuthors()) {
            List<Book> authorBooks = namedParameterJdbcTemplate.query(
                    "SELECT b.id, b.name from author a LEFT JOIN author_books ab on a.id = ab.author_id " +
                            "LEFT JOIN book b on b.id = ab.book_id WHERE a.id = :id",
                    Map.of("id", author.getId()),
                    BeanPropertyRowMapper.newInstance(Book.class));
            if (authorBooks.isEmpty()) {
                this.namedParameterJdbcTemplate.update(
                        "DELETE from author WHERE id=:id",
                        Map.of("id", author.getId()));
            }
        }
    }

    private void deleteKeywordsWithoutBooks(Book book) {
        for (Keyword keyword : book.getKeywords()) {
            List<Book> keywordBooks = namedParameterJdbcTemplate.query(
                    "SELECT b.id, b.name from keyword k LEFT JOIN book_keywords bk on k.id = bk.keyword_id " +
                            "LEFT JOIN book b on b.id = bk.book_id WHERE k.id = :id",
                    Map.of("id", keyword.getId()),
                    BeanPropertyRowMapper.newInstance(Book.class));
            if (keywordBooks.isEmpty()) {
                this.namedParameterJdbcTemplate.update(
                        "DELETE from keyword WHERE id=:id",
                        Map.of("id", keyword.getId()));
            }
        }
    }

    private Collection<Book> findByAuthors(Book book) {
        String authorIds = book.getAuthors().stream()
                .map(BaseEntity::getId)
                .map(Object::toString)
                .collect(Collectors.joining(","));

        return namedParameterJdbcTemplate.query(
                "SELECT b.id, b.name from author a LEFT JOIN author_books ab on a.id = ab.author_id " +
                        "LEFT JOIN book b on b.id = ab.book_id WHERE a.id in (:ids) order by b.name",
                Map.of("ids", authorIds),
                BeanPropertyRowMapper.newInstance(Book.class));
    }

    private void saveAuthors(final Book book) {
        List<Map<String, Object>> params = new ArrayList<>();

        for (Author author : book.getAuthors()) {
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(author);
            if (author.isNew()) {
                Number newKey = this.insertAuthor.executeAndReturnKey(parameterSource);
                author.setId(newKey.intValue());
                params.add(Map.of("book_id", book.getId(), "author_id", author.getId()));
            } else {
                this.namedParameterJdbcTemplate.update(
                        "UPDATE author SET fullname=:fullname WHERE id=:id",
                        parameterSource);
            }
        }

        this.insertAuthorBooks.executeBatch(params.toArray(Map[]::new));
    }

    private void saveKeywords(final Book book) {
        List<Map<String, Object>> params = new ArrayList<>();

        for (Keyword keyword : book.getKeywords()) {
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(keyword);
            if (keyword.isNew()) {
                Number newKey = this.insertKeyword.executeAndReturnKey(parameterSource);
                keyword.setId(newKey.intValue());
                params.add(Map.of("book_id", book.getId(), "keyword_id", keyword.getId()));
            } else {
                this.namedParameterJdbcTemplate.update(
                        "UPDATE keyword SET name=:name WHERE id=:id",
                        parameterSource);
            }
        }

        this.insertBookKeywords.executeBatch(params.toArray(Map[]::new));
    }

    private void loadAuthors(final Book book) {
        final List<Author> authors = this.namedParameterJdbcTemplate.query(
                "SELECT a.id, a.fullname FROM author_books ab " +
                        "left join author a on ab.author_id = a.id where ab.book_id = :id order by a.fullname",
                Map.of("id", book.getId()),
                BeanPropertyRowMapper.newInstance(Author.class)
        );
        book.setAuthors(authors);
    }

    private void loadKeywords(final Book book) {
        final List<Keyword> keywords = this.namedParameterJdbcTemplate.query(
                "SELECT k.id, k.name FROM book_keywords bk " +
                        "left join keyword k on bk.keyword_id = k.id where bk.book_id = :id order by k.name",
                Map.of("id", book.getId()),
                BeanPropertyRowMapper.newInstance(Keyword.class)
        );
        book.setKeywords(keywords);
    }

    private void loadBookAuthorsAndKeywords(final List<Book> books) {
        for (Book book : books) {
            loadAuthors(book);
            loadKeywords(book);
        }
    }
}