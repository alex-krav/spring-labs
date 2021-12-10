package com.example.lab.controller;

import com.example.lab.model.Author;
import com.example.lab.model.Book;
import com.example.lab.model.Keyword;
import com.example.lab.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testListBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("books/list.html"));
    }

    @Test
    public void showBook() throws Exception {
        Book book = new Book("Test book",
                Collections.singletonList(new Author("Test Author")),
                Collections.singletonList(new Keyword("fiction")));
        book.setId(1);
        when(bookService.findBookById(1)).thenReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", hasProperty("name", is("Test book"))))
                .andExpect(view().name("books/details.html"));
    }
}
