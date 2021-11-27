package com.example.lab.rest;

import com.example.lab.model.Book;
import com.example.lab.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1")
public class BookRestController {

    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<BookList> listBooks() {
        Collection<Book> books = bookService.findBooks();
        int size = books.size();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BookList(
                        size,
                        new Pagination("1", size, "2"),
                        books));
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<?> showBook(@PathVariable String bookId) {
        HttpStatus status;
        int id;
        try {
            id = Integer.parseInt(bookId);
        } catch (NumberFormatException ex) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(
                    new Error(status.value(), status.getReasonPhrase(), "Client specified non-numeric book ID."));
        }

        Book book = bookService.findBookById(id);
        if (book == null || book.getId() == null) {
            status = HttpStatus.NOT_FOUND;
            return ResponseEntity.status(status).body(
                    new Error(status.value(), status.getReasonPhrase(), "Book by specified ID is not found."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String author,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(value="sort-by", required = false) String sortBy,
                                    @RequestParam(required = false) String order) {
        Collection<Book> books;

        if (StringUtils.isNotBlank(name)) {
            books = bookService.findBooksByName(name);
        } else if (StringUtils.isNotBlank(author)) {
            books = bookService.findBooksByAuthor(author);
        } else if (StringUtils.isNotBlank(keyword)) {
            books = bookService.findBooksByKeyword(keyword);
        } else {
            books = Collections.emptyList();
        }

        SortBy sortByValue = SortBy.NAME;
        if (StringUtils.isNotBlank(sortBy)) {
            try {
                sortByValue = SortBy.valueOf(sortBy.toUpperCase());
            } catch (IllegalArgumentException ignore) {}
        }

        Order orderValue = Order.ASC;
        if (StringUtils.isNotBlank(order)) {
            try {
                orderValue = Order.valueOf(order.toUpperCase());
            } catch (IllegalArgumentException ignore) {}
        }

        return ResponseEntity.status(HttpStatus.OK)
            .body(new SearchResult(
                    new SearchFilters(name, author, keyword, sortByValue, orderValue),
                    books.size(),
                    new Pagination("1", books.size(), "2"),
                    books
            ));
    }

    @PostMapping("/books")
    public ResponseEntity<?> addBook(@RequestBody NewBook newBook) {
        HttpStatus status;
        if (newBook.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(
                    new Error(status.value(), status.getReasonPhrase(), "Client specified empty name or authors."));
        }

        Book book = bookService.save(newBook);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<?> editBook(@PathVariable String bookId,
                                      @RequestBody NewBook newBook) {
        HttpStatus status;
        int id;
        try {
            id = Integer.parseInt(bookId);
        } catch (NumberFormatException ex) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(
                    new Error(status.value(), status.getReasonPhrase(), "Client specified non-numeric book ID."));
        }

        if (newBook.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(
                    new Error(status.value(), status.getReasonPhrase(), "Client specified empty name or authors."));
        }

        Book book = bookService.findBookById(id);
        if (book == null || book.getId() == null) {
            status = HttpStatus.NOT_FOUND;
            return ResponseEntity.status(status).body(
                    new Error(status.value(), status.getReasonPhrase(), "Book by specified ID is not found."));
        }

        book.setName(newBook.getName());
        book.setAuthors(newBook.getAuthorsList());
        book.setKeywords(newBook.getKeywordsList());
        book = bookService.save(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable String bookId) {
        HttpStatus status;
        int id;
        try {
            id = Integer.parseInt(bookId);
        } catch (NumberFormatException ex) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(
                    new Error(status.value(), status.getReasonPhrase(), "Client specified non-numeric book ID."));
        }

        Book book = bookService.findBookById(id);
        if (book == null || book.getId() == null) {
            status = HttpStatus.NOT_FOUND;
            return ResponseEntity.status(status).body(
                    new Error(status.value(), status.getReasonPhrase(), "Book by specified ID is not found."));
        }

        bookService.delete(book.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
