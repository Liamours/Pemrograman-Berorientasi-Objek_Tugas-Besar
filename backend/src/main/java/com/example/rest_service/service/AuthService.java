package com.example.rest_service.service;

import com.example.rest_service.dto.*;
import com.example.rest_service.model.*;
import com.example.rest_service.repository.*;
import com.example.rest_service.security.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ClientDetailRepository clientDetailRepository;
    private final KeranjangRepository keranjangRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public AuthService(UserRepository userRepository,
                       ClientRepository clientRepository,
                       ClientDetailRepository clientDetailRepository,
                       KeranjangRepository keranjangRepository,
                       JwtTokenUtil jwtTokenUtil,
                       UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.clientDetailRepository = clientDetailRepository;
        this.keranjangRepository = keranjangRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    public ResponseEntity<ApiResponse> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Email already in use"));
        }

        try {
            Client client = new Client(request.getName(), request.getEmail(), request.getPassword());
            client.setPeran(User.Role.Client);
            client.setCreatedAt(LocalDateTime.now());
            client.setUpdatedAt(LocalDateTime.now());

            Client savedClient = clientRepository.save(client);

            ClientDetail detail = new ClientDetail();
            detail.setUserId(savedClient.getId());
            detail.setIsmember(false);
            detail.setAlamat(null);
            clientDetailRepository.save(detail);

            savedClient.setClientDetails(detail);
            clientRepository.save(savedClient);

            Keranjang keranjang = new Keranjang();
            keranjang.setUser(savedClient);
            keranjangRepository.save(keranjang);

            return ResponseEntity.ok(new ApiResponse(true, "Registration successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Registration failed: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse> login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty() || !request.getPassword().equals(userOpt.get().getPassword())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid email or password"));
        }

        User user = userOpt.get();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("token", token);
        responseData.put("userId", user.getId());
        responseData.put("email", user.getEmail());
        responseData.put("role", user.getPeran());

        if (user instanceof Client client) {
            responseData.put("isMember", client.isMember());
            responseData.put("address", client.getAlamat());
        }

        return ResponseEntity.ok(new ApiResponse(true, "Login successful", responseData));
    }

    public ResponseEntity<ApiResponse> logout() {
        return ResponseEntity.ok(new ApiResponse(true, "Logout successful"));
    }
}