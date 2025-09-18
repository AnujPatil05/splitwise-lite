package com.example.splitwise_lite.service;

import com.example.splitwise_lite.model.User;
import com.example.splitwise_lite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {

        String plainTextPassword = user.getPassword();


        String encodedPassword = passwordEncoder.encode(plainTextPassword);


        user.setPassword(encodedPassword);


        return userRepository.save(user);
    }
}