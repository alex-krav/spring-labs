package com.example.lab.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class Form {
    private int id;
    private String name;
    private String authors;
    private String keywords;

    public boolean isEmpty() {
        return StringUtils.isBlank(name) || StringUtils.isBlank(authors);
    }
}
