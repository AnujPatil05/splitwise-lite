package com.example.splitwise_lite.controller;


import com.example.splitwise_lite.dto.AuthRequestDTO;
import com.example.splitwise_lite.dto.AuthResponseDTO;
import com.example.splitwise_lite.model.User;
import com.example.splitwise_lite.exception.UserNotFoundException;
import com.example.splitwise_lite.repository.UserRepository;
import com.example.splitwise_lite.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO request) {
        // Find the user by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found :" + request.getUsername()));

        // Check if the provided password matches the stored hashed password
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // If passwords match, generate a JWT
            String token = jwtUtil.generateToken(user);
            return new AuthResponseDTO(token);
        } else {
            // If passwords don't match, throw an exception
            throw new RuntimeException("Invalid credentials");
        }
    }
}