package com.example.rest_service.controller;

import com.example.rest_service.model.Barang;
import com.example.rest_service.service.BarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/barang")
public class BarangController {

    @Autowired
    private BarangService barangService;

    @GetMapping
    public ResponseEntity<List<Barang>> getAllBarang() {
        List<Barang> barangList = barangService.getAllBarang();
        return ResponseEntity.ok(barangList);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('Admin')") // Pastikan hanya admin yang bisa akses
    public ResponseEntity<Barang> addProduct(@RequestBody Barang product) {
        Barang savedProduct = barangService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
}
