package com.example.rest_service.controller;

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
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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