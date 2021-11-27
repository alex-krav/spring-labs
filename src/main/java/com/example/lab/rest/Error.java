package com.example.lab.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error {

    private int code;
    private String status;
    private String message;
}
