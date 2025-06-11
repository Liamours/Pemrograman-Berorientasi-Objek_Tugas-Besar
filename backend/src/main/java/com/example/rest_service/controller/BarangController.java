package com.example.rest_service.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.rest_service.dto.BarangIdRequest;
import com.example.rest_service.model.Barang;
import com.example.rest_service.service.BarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest_service.dto.NewBarangRequest;
import com.example.rest_service.dto.DeletebyIDRequest;
import com.example.rest_service.dto.ApiResponse;
import com.example.rest_service.dto.UpdateBarangRequest;
import com.example.rest_service.dto.UpdateStockRequest;

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



    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse> deleteBarang(@Valid @RequestBody DeletebyIDRequest barangIdRequest) {
        // 1. Check if barang exists
        boolean isDeleted = barangService.deleteBarang(barangIdRequest.getBarangId());

        // 2. Handle deletion success or failure
        if (isDeleted) {
            // Prepare additional data if needed (e.g., barangId or related info)
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("barangId", barangIdRequest.getBarangId());

            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "Barang telah dihapus", responseData));
        } else {
            // Handle failure case, e.g., Barang not found
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Barang tidak ditemukan"));
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse> updateBarang(@Valid @RequestBody UpdateBarangRequest request) {
        // Pastikan barangId tidak null
        if (request.getBarangId() == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Barang ID cannot be null"));
        }

        Barang updatedBarang = barangService.updateBarang(request);
        if (updatedBarang != null) {
            return ResponseEntity.ok(new ApiResponse(true, "Data barang berhasil diubah", updatedBarang));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Barang tidak ditemukan"));
        }
    }

    @PutMapping("/update/stock")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse> updateStock(@Valid @RequestBody UpdateStockRequest request) {
        Barang updatedBarang = barangService.updateStock(request);

        if (updatedBarang != null) {
            return ResponseEntity.ok(new ApiResponse(true, "Stock updated successfully", updatedBarang));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Barang not found"));
        }
    }
}
