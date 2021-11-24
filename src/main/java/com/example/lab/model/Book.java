package com.example.lab.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

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
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(name = "book_keywords", joinColumns =
    @JoinColumn(name = "keyword_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Keyword> keywords;
}
