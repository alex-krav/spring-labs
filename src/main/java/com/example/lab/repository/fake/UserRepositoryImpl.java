package com.example.lab.repository.fake;

import com.example.lab.model.User;
import com.example.lab.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private List<User> users = new ArrayList<>();

    public UserRepositoryImpl() {
        users.add(new User("Alex","Kravchuk"));
        users.add(new User("John","Doe"));
        users.add(new User("Elon","Musk"));
    }

    @Override
    public Collection<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public Collection<User> findByLastName(String lastName) {
        return users
                .stream()
                .filter(u -> u.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public User findById(int id) {
        return users.get(id);
    }

    @Override
    public void save(User user) {
        // no-op
    }
}
