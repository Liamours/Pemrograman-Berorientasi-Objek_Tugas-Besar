package com.example.rest_service.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import com.example.rest_service.repository.UserRepository;
import com.example.rest_service.security.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import com.example.rest_service.model.User;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        // Dapatkan username/email dari token lewat objek Authentication
        String email = authentication.getName();

        // Cari user berdasarkan email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();

        // Buat DTO atau langsung kirim data user (hindari kirim password)
        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("email", user.getEmail());
        profile.put("name", user.getName());
        // tambahkan field lain sesuai kebutuhan

        return ResponseEntity.ok(profile);
    }
}
