package com.example.lab.service;

import com.example.lab.model.Book;
import com.example.lab.repository.BookRepository;
import com.example.lab.rest.NewBook;
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
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book save(NewBook newBook) {
        Book book = new Book();
        book.setName(newBook.getName());
        book.setAuthors(newBook.getAuthorsList());
        book.setKeywords(newBook.getKeywordsList());

        return bookRepository.save(book);
    }

    @Override
    public void delete(int id) {
        bookRepository.delete(id);
    }
}
