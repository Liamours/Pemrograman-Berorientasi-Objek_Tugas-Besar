/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.rest_service.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.rest_service.model.Client;
import com.example.rest_service.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rest_service.dto.ApiResponse;
import com.example.rest_service.dto.LoginRequest;
import com.example.rest_service.dto.RegisterRequest;
import com.example.rest_service.model.User;
import com.example.rest_service.repository.UserRepository;
import com.example.rest_service.security.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final ClientRepository clientRepository;

    public AuthController(UserRepository userRepository,
                          JwtTokenUtil jwtTokenUtil,
                          UserDetailsService userDetailsService,ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.clientRepository = clientRepository;
    }

    // REGISTER
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Email udah dipake"));
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setPeran(User.Role.Client);
        User savedUser = userRepository.save(user);
        Client client = new Client();
        client.setUser(savedUser);
        client.setIsmember(false);
        client.setAlamat(null);
        clientRepository.save(client);


        return ResponseEntity.ok(new ApiResponse(true, "Register sukses, silakan login!"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        // 1. Find user by email
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid email or password"));
        }

        User user = userOptional.get();

        // 2. Verify password with BCrypt
        if (!loginRequest.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest()
            .body(new ApiResponse(false, "Invalid email or password"));
        }

        // 3. Load UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        // 4. Generate JWT token
        String token = jwtTokenUtil.generateToken(userDetails);

        // 5. Prepare response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("token", token);
        responseData.put("userId", user.getId());
        responseData.put("email", user.getEmail());
        responseData.put("role", user.getPeran());

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "Login successful", responseData));
    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        // Karena tanpa blacklist, logout cukup hapus token di client
        return ResponseEntity.ok(new ApiResponse(true, "Logout successful"));
    }
}
