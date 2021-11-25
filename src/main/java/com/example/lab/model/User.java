package com.example.lab.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class User extends BaseEntity {
    @Column(name = "name")
    @NonNull
    @Size(max = 60)
    private String name;

    @Column(name = "email")
    @NonNull
    @Size(max = 40)
    private String email;

    @Column(name = "password")
    @NonNull
    @Size(max = 100)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns =
    @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Role> roles;
}
