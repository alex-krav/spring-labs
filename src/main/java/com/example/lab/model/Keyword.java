package com.example.lab.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "keyword")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Keyword extends BaseEntity {

    @Column(name = "name")
    @NonNull
    @Size(max = 60)
    private String name;

    @ManyToMany
    @JoinTable(name = "book_keywords", joinColumns =
    @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private Set<Book> books;
}
