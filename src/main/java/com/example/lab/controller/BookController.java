package com.example.lab.controller;

import com.example.lab.model.Book;
import com.example.lab.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Collections;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ModelAndView listBooks() {
        ModelAndView mav = new ModelAndView("books/list.html");
        mav.addObject("books", bookService.findBooks());
        return mav;
    }

    @GetMapping("/books/{bookId}")
    public ModelAndView showBook(@PathVariable("bookId") int bookId) {
        ModelAndView mav = new ModelAndView("books/details.html");
        mav.addObject("book", bookService.findBookById(bookId));
        return mav;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(name = "name", required = false) String name,
                               @RequestParam(name = "author", required = false) String author,
                               @RequestParam(name = "keyword", required = false) String keyword) {
        ModelAndView mav = new ModelAndView("books/search.html");
        Collection<Book> books;
        String query;

        if (StringUtils.isNotBlank(name)) {
            books = bookService.findBooksByName(name);
            query = "name = " + name;
        } else if (StringUtils.isNotBlank(author)) {
            books = bookService.findBooksByAuthor(author);
            query = "author = " + author;
        } else if (StringUtils.isNotBlank(keyword)) {
            books = bookService.findBooksByKeyword(keyword);
            query = "keyword = " + keyword;
        } else {
            books = Collections.emptyList();
            query = StringUtils.EMPTY;
        }

        mav.addObject("books", books);
        mav.addObject("query", query);
        return mav;
    }
}
