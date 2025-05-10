package com.example.growandcheer.service;

import com.example.growandcheer.dto.LoginRequest;
import com.example.growandcheer.dto.RegisterRequest;
import com.example.growandcheer.model.User;
import com.example.growandcheer.repository.UserRepository;
import com.example.growandcheer.service.AuthService;
import com.example.growandcheer.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.growandcheer.config.SecurityConfig;

@Service
public class impl implements AuthService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User authenticate(LoginRequest req) {
        System.out.println("Authenticating user with email: " + req.getEmail());
        System.out.println("PasswordEncoder class: " + passwordEncoder.getClass().getName());
        if (req.getEmail().isBlank() || req.getPassword().isBlank()) {
            System.out.println("Email or password is blank");
            throw new IllegalArgumentException("Invalid input: email and password must be provided");
        }

        //SQL-injection pattern check
        String combined = (req.getEmail() + " " + req.getPassword()).toLowerCase();
        String[] sqlPatterns = { "select ", "insert ", "update ", "delete ",
                "--", ";", " or ", " and ", "/*", "*/", "xp_" };
        for (String p : sqlPatterns) {
            if (combined.contains(p)) {
                System.out.println("Input contains forbidden pattern: " + p);
                throw new IllegalArgumentException("Input mengandung karakter atau pola yang tidak diizinkan");
            }
        }


        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> {
                    System.out.println("User not found with email: " + req.getEmail());
                    return new IllegalArgumentException("Wrong credentials");
                });

        boolean matches = passwordEncoder.matches(req.getPassword(), user.getPassword());
        System.out.println("Password match result: " + matches);
        if (!matches) {
            System.out.println("Password does not match for user: " + req.getEmail());
            throw new IllegalArgumentException("Wrong credentials");
        }

        System.out.println("User authenticated successfully: " + req.getEmail());
        return user;
    }

    @Override
    public User register(RegisterRequest req) {
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email sudah terdaftar");
        }
        User user = new User();
        user.setEmail(req.getEmail());
        user.setName(req.getName());
        String hashed = passwordEncoder.encode(req.getPassword());
        user.setPassword(hashed);

        return userRepo.save(user);
    }
}
