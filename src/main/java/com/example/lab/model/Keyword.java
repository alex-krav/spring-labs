package com.example.lab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

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

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "book_keywords", joinColumns =
    @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private List<Book> books;

    @Override
    public String toString() {
        return name;
    }
}
