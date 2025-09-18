package com.example.splitwise_lite.service;

import com.example.splitwise_lite.model.User;
import com.example.splitwise_lite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {

        return userRepository.save(user);
    }
}