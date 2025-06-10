package com.example.rest_service.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.rest_service.model.Barang;
import com.example.rest_service.service.BarangService;
import com.example.rest_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest_service.model.User;
import com.example.rest_service.dto.RequestBarangFilter;
import java.util.List;

@RestController
@RequestMapping("/barang")
public class BarangController {

    @Autowired
    private BarangService barangService;

    @Autowired
    private UserRepository userRepository;

    // Endpoint to get all barang with optional filters
    @GetMapping("/getAll")
    public ResponseEntity<List<Barang>> getAllBarang(@RequestBody RequestBarangFilter filter) {
        List<Barang> barangList = barangService.getBarangWithFilters(filter);
        return ResponseEntity.ok(barangList);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> createProduct(@RequestBody Barang product, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email); // Mendapatkan user berdasarkan email

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();  // Ambil user dari Optional
            if ("Admin".equals(user.getPeran())) {
                barangService.addProduct(product);  // Proses penyimpanan barang
                return ResponseEntity.ok("Product created successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to create a product.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

}
