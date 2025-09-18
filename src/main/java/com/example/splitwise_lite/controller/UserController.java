package com.example.splitwise_lite.controller;

import com.example.splitwise_lite.model.User;
import com.example.splitwise_lite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {

        return userService.createUser(user);
    }
}