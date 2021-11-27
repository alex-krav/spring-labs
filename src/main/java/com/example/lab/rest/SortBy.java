package com.example.lab.rest;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SortBy {
    NAME("name"),
    AUTHOR("author");

    private final String sortBy;

    private SortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    @JsonValue
    public String value() {
        return sortBy;
    }
}
