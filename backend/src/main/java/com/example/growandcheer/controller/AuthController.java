package com.example.growandcheer.controller;


import com.example.growandcheer.dto.ApiResponse;
import com.example.growandcheer.dto.LoginRequest;
import com.example.growandcheer.dto.RegisterRequest;
import com.example.growandcheer.model.User;
import com.example.growandcheer.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody RegisterRequest req) {
        try {
            User newUser = authService.register(req);
            return ResponseEntity
                    .status(201)
                    .body(new ApiResponse<>("success", newUser,"Registrasi Sukses"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new ApiResponse<>("error", e.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@Valid @RequestBody LoginRequest req) {
        try {
            User user = authService.authenticate(req);
            return ResponseEntity.ok(new ApiResponse<>("success", user, "Login Sukses"));
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg.startsWith("Invalid input")) {
                return ResponseEntity
                        .badRequest()
                        .body(new ApiResponse<>("invalid", msg));
            } else if (msg.contains("tidak diizinkan") || msg.contains("kosong")) {
                return ResponseEntity
                        .status(400)
                        .body(new ApiResponse<>("invalid_input", msg));
            } else {
                // default for any other IllegalArgumentException (e.g. wrong credentials)
                return ResponseEntity
                        .status(401)
                        .body(new ApiResponse<>("wrong_credentials", msg));
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(new ApiResponse<>("error", "Internal server error"));
        }
    }

}

