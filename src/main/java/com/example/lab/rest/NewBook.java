package com.example.lab.rest;

import com.example.lab.model.Author;
import com.example.lab.model.Keyword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class NewBook {

    private String name;
    private String authors;
    private String keywords;

    public boolean isEmpty() {
        return StringUtils.isBlank(name) || StringUtils.isBlank(authors);
    }

    public List<Author> getAuthorsList() {
        if (authors == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(authors.split(","))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(Author::new)
                .collect(Collectors.toList());
    }

    public List<Keyword> getKeywordsList() {
        if (keywords == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(keywords.split(","))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(Keyword::new)
                .collect(Collectors.toList());
    }
}
