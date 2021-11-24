package com.example.lab.repository;

import com.example.lab.model.User;

import java.util.Collection;

public interface UserRepository {
    Collection<User> findAll();
    Collection<User> findByName(String name);
    User findById(int id);
    void save(User user);
}
