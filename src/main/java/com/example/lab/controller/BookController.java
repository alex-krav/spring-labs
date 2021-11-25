package com.example.lab.controller;

import com.example.lab.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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
}
