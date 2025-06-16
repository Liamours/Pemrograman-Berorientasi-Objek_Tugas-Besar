package com.example.rest_service.controller;

import com.example.rest_service.dto.*;
import com.example.rest_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController{

    @Autowired
    private UserService userService;

    @GetMapping("/profile/admin")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse> getAdminProfile(Authentication authentication) {
        try {
            Map<String, Object> profile = userService.getAdminProfile(authentication.getName());
            return ResponseEntity.ok(
                    new ApiResponse(true, "Admin profile retrieved successfully", profile)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/profile/client")
    @PreAuthorize("hasRole('Client')")
    public ResponseEntity<ApiResponse> getClientProfile(Authentication authentication) {
        try {
            Map<String, Object> profile = userService.getClientProfile(authentication.getName());
            return ResponseEntity.ok(
                    new ApiResponse(true, "Client profile retrieved successfully", profile)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/profile/update")
    public ResponseEntity<ApiResponse> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            FieldError firstError = bindingResult.getFieldErrors().get(0);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false,
                            "Validation error: " + firstError.getField() + " " + firstError.getDefaultMessage()));
        }

        try {
            userService.updateProfile(authentication.getName(), request);
            return ResponseEntity.ok(
                    new ApiResponse(true, "Profile updated successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update profile: " + e.getMessage()));
        }
    }

    @PutMapping("/password/change")
    public ResponseEntity<ApiResponse> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request) {
        try {
            userService.changePassword(authentication.getName(), request);
            return ResponseEntity.ok(
                    new ApiResponse(true, "Password changed successfully")
            );
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ApiResponse(false, e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Password change failed: " + e.getMessage()));
        }
    }

    

    @PutMapping("/member")
    @PreAuthorize("hasRole('Client')")
    public ResponseEntity<ApiResponse> upgradeToMember(Authentication authentication) {
        try {
            userService.upgradeToMember(authentication.getName());
            return ResponseEntity.ok(
                    new ApiResponse(true, "Upgraded to member successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Upgrade failed: " + e.getMessage()));
        }
    }

}