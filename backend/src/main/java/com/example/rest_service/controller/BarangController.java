package com.example.rest_service.controller;

import java.time.LocalDateTime;
import java.util.*;

import com.example.rest_service.dto.*;
import com.example.rest_service.model.Barang;
import com.example.rest_service.service.BarangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/barang")
public class BarangController {

    @Autowired
    private BarangService barangService;

    // --- [GET] List Semua Barang dengan Optional Filter (User Friendly)
    @PostMapping("/list")
    public ResponseEntity<ApiResponse> getAllBarang(@RequestBody BarangFilterRequest filterRequest) {
        List<Barang> filteredBarang = barangService.getBarangByFilter(filterRequest);
        List<Map<String, Object>> formattedData = new ArrayList<>();

        for (Barang barang : filteredBarang) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", barang.getBarangId());
            data.put("name", barang.getNamaBarang());
            data.put("price", barang.getHarga());
            data.put("category", barang.getTipeBarang());
            data.put("image_url", barang.getImageUrl());
            data.put("stock", barang.getStokBarang());
            formattedData.add(data);
        }

        String message = formattedData.isEmpty() ? "No matching items found" : "List barang berhasil diambil";
        return ResponseEntity.ok(new ApiResponse(true, message, formattedData));
    }

    // --- [GET] Get Detail Barang by ID
    @PostMapping("/detail")
    public ResponseEntity<ApiResponse> getBarangDetail(@RequestBody BarangIdRequest request) {
        Barang barang = barangService.getBarangById(request.getBarangId());

        if (barang == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Barang tidak ditemukan", null));
        }

        Map<String, Object> data = new HashMap<>();
        data.put("barang_id", barang.getBarangId());
        data.put("nama_barang", barang.getNamaBarang());
        data.put("deskripsi_barang", barang.getDeskripsiBarang());
        data.put("harga", barang.getHarga());
        data.put("tipe_barang_id", barang.getTipeBarang());
        data.put("image_url", barang.getImageUrl());
        data.put("stock", barang.getStokBarang());

        return ResponseEntity.ok(new ApiResponse(true, "Data barang berhasil diambil", data));
    }

    // --- [POST] Tambah Barang Baru (ADMIN ONLY)
    @PostMapping("/new")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse> addProduct(@Valid @RequestBody NewBarangRequest dto) {
        Barang product = new Barang();
        product.setNamaBarang(dto.getNamaBarang());
        product.setDeskripsiBarang(dto.getDeskripsiBarang());
        product.setHarga(dto.getHarga());
        product.setTipeBarang(dto.getTipeBarang());
        product.setImageUrl(dto.getImageUrl());
        product.setStokBarang(dto.getStokBarang());
        product.setCreatedAt(LocalDateTime.now());

        Barang saved = barangService.addProduct(product);

        Map<String, Object> data = new HashMap<>();
        data.put("barang_id", saved.getBarangId());
        data.put("nama_barang", saved.getNamaBarang());
        data.put("deskripsi_barang", saved.getDeskripsiBarang());
        data.put("harga", saved.getHarga());
        data.put("tipe_barang_id", saved.getTipeBarang());
        data.put("image_url", saved.getImageUrl());
        data.put("stock", saved.getStokBarang());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Barang baru berhasil ditambahkan", data));
    }

    // --- [PUT] Update Detail Barang (ADMIN ONLY)
    @PutMapping("/update/detail")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse> updateBarang(@Valid @RequestBody UpdateBarangRequest request) {
        if (request.getBarangId() == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Barang ID tidak boleh null"));
        }

        Barang updated = barangService.updateBarang(request);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Barang tidak ditemukan"));
        }

        return ResponseEntity.ok(new ApiResponse(true, "Data barang berhasil diubah", updated));
    }

    // --- [PUT] Update Stock Barang (ADMIN ONLY)
    @PutMapping("/update/stock")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse> updateStock(@Valid @RequestBody UpdateStockRequest request) {
        Barang updated = barangService.updateStock(request);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Barang tidak ditemukan"));
        }

        return ResponseEntity.ok(new ApiResponse(true, "Stock updated successfully", updated));
    }

    // --- [DELETE] Hapus Barang (ADMIN ONLY)
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse> deleteBarang(@Valid @RequestBody DeletebyIDRequest request) {
        boolean isDeleted = barangService.deleteBarang(request.getBarangId());

        if (!isDeleted) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Barang tidak ditemukan"));
        }

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("barangId", request.getBarangId());
        return ResponseEntity.ok(new ApiResponse(true, "Barang telah dihapus", responseData));
    }

    // --- [GET] Simple Fetch All (Non-Filtered, raw model) -- Optional testing
    @GetMapping("/raw")
    public ResponseEntity<List<Barang>> getAllBarangRaw() {
        List<Barang> barangList = barangService.getAllBarang();
        return ResponseEntity.ok(barangList);
    }
}
