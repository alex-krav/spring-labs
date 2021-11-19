package com.example.lab.service;

import com.example.lab.model.User;
import com.example.lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
