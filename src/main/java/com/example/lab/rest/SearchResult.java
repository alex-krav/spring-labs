package com.example.lab.rest;

import com.example.lab.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class SearchResult {

    private SearchFilters search_filters;
    private int total_size;
    private Pagination pagination;
    private Collection<Book> search_results;
}
