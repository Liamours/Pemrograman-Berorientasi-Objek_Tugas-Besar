package com.example.rest_service.controller;

import com.example.rest_service.dto.*;
import com.example.rest_service.model.*;
import com.example.rest_service.repository.*;
import com.example.rest_service.security.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    private final ClientDetailRepository clientDetailRepository;
    private final KeranjangRepository keranjangRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public AuthController(UserRepository userRepository,
                          ClientRepository clientRepository,
                          AdminRepository adminRepository,
                          ClientDetailRepository clientDetailRepository,
                          KeranjangRepository keranjangRepository,
                          JwtTokenUtil jwtTokenUtil,
                          UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.clientDetailRepository = clientDetailRepository;
        this.keranjangRepository = keranjangRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Email already in use"));
        }

        try {
            // Create new Client (which extends User)
            Client client = new Client(
                    registerRequest.getName(),
                    registerRequest.getEmail(),
                    registerRequest.getPassword()
            );
            client.setPeran(User.Role.Client);
            client.setCreatedAt(LocalDateTime.now());
            client.setUpdatedAt(LocalDateTime.now());

            // First save the client to generate the ID
            Client savedClient = clientRepository.save(client);

            // Create and associate ClientDetail with the client's ID
            ClientDetail clientDetail = new ClientDetail();
            clientDetail.setUserId(savedClient.getId()); // Set the ID manually
            clientDetail.setIsmember(false);
            clientDetail.setAlamat(null);
            clientDetailRepository.save(clientDetail);

            // Set the clientDetail to the client
            savedClient.setClientDetails(clientDetail);
            clientRepository.save(savedClient); // Update the client with the detail

            // Create shopping cart for the client
            Keranjang keranjang = new Keranjang();
            keranjang.setUser(savedClient);
            keranjangRepository.save(keranjang);

            return ResponseEntity.ok(new ApiResponse(true, "Registration successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid email or password"));
        }

        User user = userOptional.get();

        // In production, use password encoder to match hashed passwords
        if (!loginRequest.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid email or password"));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("token", token);
        responseData.put("userId", user.getId());
        responseData.put("email", user.getEmail());
        responseData.put("role", user.getPeran());

        // Add additional client-specific data if user is a client
        if (user instanceof Client client) {
            responseData.put("isMember", client.isMember());
            responseData.put("address", client.getAlamat());
        }

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "Login successful", responseData));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
        // In a stateless JWT system, logout is handled client-side by discarding the token
        // If you need server-side logout, consider implementing a token blacklist
        return ResponseEntity.ok(new ApiResponse(true, "Logout successful"));
    }
}