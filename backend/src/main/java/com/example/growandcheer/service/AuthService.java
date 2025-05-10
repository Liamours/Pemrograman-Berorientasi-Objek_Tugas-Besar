package com.example.growandcheer.service;

import com.example.growandcheer.dto.LoginRequest;
import com.example.growandcheer.model.User;
import com.example.growandcheer.dto.RegisterRequest;   // ← add this import

public interface AuthService {
    User authenticate(LoginRequest request);
    User register(RegisterRequest request);
}
