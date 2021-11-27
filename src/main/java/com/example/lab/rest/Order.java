package com.example.lab.rest;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Order {
    ASC("asc"),
    DESC("desc");

    private final String order;

    private Order(String order) {
        this.order = order;
    }

    @JsonValue
    public String value() {
        return order;
    }
}
