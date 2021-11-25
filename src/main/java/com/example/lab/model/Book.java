package com.example.lab.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "book")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Book extends BaseEntity {

    @Column(name = "name")
    @NonNull
    @Size(max = 255)
    private String name;

    @ManyToMany
    @JoinTable(name = "author_books", joinColumns =
    @JoinColumn(name = "author_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Author> authors;

    @ManyToMany
    @JoinTable(name = "book_keywords", joinColumns =
    @JoinColumn(name = "keyword_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Keyword> keywords;

    @Override
    public String toString() {
        return name;
    }
}
