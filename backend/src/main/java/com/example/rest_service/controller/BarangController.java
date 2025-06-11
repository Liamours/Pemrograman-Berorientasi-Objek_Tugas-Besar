package com.example.rest_service.controller;

import java.util.*;

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

import com.example.rest_service.dto.BarangFilterRequest;
import com.example.rest_service.dto.NewBarangRequest;
import com.example.rest_service.dto.DeletebyIDRequest;
import com.example.rest_service.dto.ApiResponse;
import com.example.rest_service.dto.UpdateBarangRequest;
import com.example.rest_service.dto.UpdateStockRequest;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/barang")
public class BarangController {

    @Autowired
    private BarangService barangService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllBarang(@RequestBody BarangFilterRequest filterRequest) {
        List<Barang> filteredBarang = barangService.getFilteredBarang(filterRequest);

        if (filteredBarang.isEmpty()) {
            // Return empty list if no matching results
            return ResponseEntity.ok(new ApiResponse(true, "No matching items found", new ArrayList<>()));
        }

        // Format the filteredBarang response
        List<Map<String, Object>> formattedData = new ArrayList<>();

        for (Barang barang : filteredBarang) {
            Map<String, Object> formattedBarang = new HashMap<>();
            formattedBarang.put("id", barang.getBarangId());
            formattedBarang.put("name", barang.getNamaBarang());
            formattedBarang.put("price", barang.getHarga());
            formattedBarang.put("category", barang.getTipeBarang());
            formattedBarang.put("image_url", barang.getImageUrl());
            formattedBarang.put("stock", barang.getStokBarang());

            formattedData.add(formattedBarang);
        }

        ApiResponse response = new ApiResponse(true, "List barang berhasil diambil", formattedData);
        return ResponseEntity.ok(response);
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

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse> getBarangDetail(@RequestBody BarangIdRequest request) {
        // Ambil barang berdasarkan ID
        Barang barang = barangService.getBarangById(request.getBarangId());

        // Jika barang tidak ditemukan
        if (barang == null) {
            ApiResponse response = new ApiResponse(false, "Barang tidak ditemukan", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Jika barang ditemukan, format response sesuai dengan yang diinginkan
        Map<String, Object> formattedBarang = new HashMap<>();
        formattedBarang.put("barang_id", barang.getBarangId());
        formattedBarang.put("nama_barang", barang.getNamaBarang());
        formattedBarang.put("deskripsi_barang", barang.getDeskripsiBarang());
        formattedBarang.put("harga", barang.getHarga());
        formattedBarang.put("tipe_barang_id", barang.getTipeBarang());
        formattedBarang.put("image_url", barang.getImageUrl());
        formattedBarang.put("stock", barang.getStokBarang());

        ApiResponse response = new ApiResponse(true, "Data barang berhasil diambil", formattedBarang);
        return ResponseEntity.ok(response);
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

    @PutMapping("/update/detail")
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
