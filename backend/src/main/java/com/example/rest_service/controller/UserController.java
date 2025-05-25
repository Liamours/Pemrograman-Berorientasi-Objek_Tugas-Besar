package com.example.rest_service.controller;

<<<<<<< HEAD
import com.example.rest_service.dto.ApiResponse;
import com.example.rest_service.model.User;
import com.example.rest_service.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
=======
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
>>>>>>> 9059544572f0616e8669f338974d0727e90c828b
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

<<<<<<< HEAD
    // Endpoint untuk mendapatkan data user yang sedang login
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());
        userData.put("createdAt", user.getCreatedAt());

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "User profile retrieved", userData));
    }

    // Endpoint untuk update profile (contoh tambahan)
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> request
    ) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update field yang diizinkan
        if (request.containsKey("name")) {
            user.setName(request.get("name"));
        }

        userRepository.save(user);

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "Profile updated successfully"));
    }
}
=======
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
>>>>>>> 9059544572f0616e8669f338974d0727e90c828b
