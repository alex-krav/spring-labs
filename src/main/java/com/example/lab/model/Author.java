package com.example.lab.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "author")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Author extends BaseEntity {

    @Column(name = "fullname")
    @NonNull
    @Size(max = 255)
    private String fullname;

    @ManyToMany
    @JoinTable(name = "author_books", joinColumns =
    @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Book> books;
}
