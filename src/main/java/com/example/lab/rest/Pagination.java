package com.example.lab.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pagination {

    private String page_token;
    private int page_size;
    private String next_page_token;
}
