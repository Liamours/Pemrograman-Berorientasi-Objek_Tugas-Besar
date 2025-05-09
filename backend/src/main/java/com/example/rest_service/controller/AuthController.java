/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rest_service.controller;

import com.example.rest_service.dto.ApiResponse;
import com.example.rest_service.dto.LoginRequest;
import com.example.rest_service.model.User;
import com.example.rest_service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@CrossOrigin(origins = "http://localhost:3000") // Allow only React
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserRepository userRepository;
    
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        // 1. First inject BCryptPasswordEncoder (add this at class level)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 2. Find user by email
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid email or password"));
        }

        User user = userOptional.get();

        // 3. Updated password check using BCrypt
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid email or password"));
        }

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "Login successful", user));
    }
}
