package com.example.rest_service.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import com.example.rest_service.repository.UserRepository;
import com.example.rest_service.repository.ClientRepository;
import com.example.rest_service.security.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import com.example.rest_service.model.User;
import com.example.rest_service.model.Client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public UserController(UserRepository userRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/profile/admin")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getAdminProfile(Authentication authentication) {
        String email = authentication.getName();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();

        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("email", user.getEmail());
        profile.put("name", user.getName());
        profile.put("role", user.getPeran());
        profile.put("createdAt", user.getCreatedAt());
        profile.put("updatedAt", user.getUpdatedAt());

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profile/client")
    @PreAuthorize("hasRole('Client')")
    public ResponseEntity<?> getClientProfile(Authentication authentication) {
        String email = authentication.getName();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();
        Optional<Client> clientOptional = clientRepository.findById(user.getId());
        if (clientOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client details not found");
        }

        Client client = clientOptional.get();

        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("email", user.getEmail());
        profile.put("name", user.getName());
        profile.put("role", user.getPeran());
        profile.put("isMember", client.isIsmember());
        profile.put("address", client.getAlamat());
        profile.put("createdAt", user.getCreatedAt());
        profile.put("updatedAt", user.getUpdatedAt());

        return ResponseEntity.ok(profile);
    }
}