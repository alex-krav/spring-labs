package com.example.lab.service;

import com.example.lab.model.Author;
import com.example.lab.model.Book;
import com.example.lab.model.Form;
import com.example.lab.model.Keyword;
import com.example.lab.repository.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Collection<Book> findBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Collection<Book> findBooksByName(String name) {
        return bookRepository.findByName(name);
    }

    @Override
    public Collection<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public Collection<Book> findBooksByKeyword(String keyword) {
        return bookRepository.findByKeyword(keyword);
    }

    @Override
    public Book findBookById(int id) {
        return bookRepository.findById(id);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book save(Form form) {
        Book book = new Book();
        book.setName(form.getName());

        List<Author> authors = Arrays.stream(form.getAuthors().split(","))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(Author::new)
                .collect(Collectors.toList());
        book.setAuthors(authors);

        List<Keyword> keywords = Arrays.stream(form.getKeywords().split(","))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(Keyword::new)
                .collect(Collectors.toList());
        book.setKeywords(keywords);

        return bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
