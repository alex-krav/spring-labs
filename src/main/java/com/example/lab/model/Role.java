package com.example.lab.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {
    @Column(name = "name")
    @NonNull
    @Size(max = 60)
    private String name;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns =
    @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<User> users;
}
