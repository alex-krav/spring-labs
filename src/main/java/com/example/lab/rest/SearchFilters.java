package com.example.lab.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchFilters {

    private String name;
    private String author;
    private String keyword;
    private SortBy sort_by;
    private Order order;
}
