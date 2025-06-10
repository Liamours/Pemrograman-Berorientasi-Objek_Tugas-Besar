package com.example.rest_service.controller;

import com.example.rest_service.dto.BarangIdRequest;
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
import com.example.rest_service.dto.NewBarangRequest;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
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
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Barang> addProduct(@Valid @RequestBody NewBarangRequest dto) {
        Barang product = new Barang();
        product.setNamaBarang(dto.getNamaBarang());
        product.setDeskripsiBarang(dto.getDeskripsiBarang());
        product.setHarga(dto.getHarga());
        product.setTipeBarang(dto.getTipeBarang());
        product.setImageUrl(dto.getImageUrl());
        product.setStokBarang(dto.getStokBarang());
        product.setCreatedAt(LocalDateTime.now());

        Barang savedProduct = barangService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
    @PostMapping("/detail")
    public ResponseEntity<Barang> getBarangDetail(@Valid @RequestBody BarangIdRequest request) {
        Barang barang = barangService.getBarangById(request.getBarangId());
        if (barang == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(barang);

    }
}
