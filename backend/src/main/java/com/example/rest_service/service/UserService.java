package com.example.rest_service.service;

import com.example.rest_service.dto.RegisterRequest;
import com.example.rest_service.model.User;
import com.example.rest_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email udah dipake");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // plaintext langsung masuk DB

        userRepository.save(user);
    }
}
