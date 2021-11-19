package com.example.lab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class User extends BaseEntity {
    protected String firstName;
    protected String lastName;
}
