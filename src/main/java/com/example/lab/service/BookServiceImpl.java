package com.example.lab.service;

import com.example.lab.model.Book;
import com.example.lab.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
