package com.example.lab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

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

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "author_books", joinColumns =
    @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Book> books;

    @Override
    public String toString() {
        return fullname;
    }
}
