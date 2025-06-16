package com.example.rest_service.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest_service.dto.ApiResponse;
import com.example.rest_service.dto.BarangFilterRequest;
import com.example.rest_service.dto.BarangIdRequest;
import com.example.rest_service.dto.DeletebyIDRequest;
import com.example.rest_service.dto.NewBarangRequest;
import com.example.rest_service.dto.UpdateBarangRequest;
import com.example.rest_service.model.Barang;
import com.example.rest_service.service.BarangService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/barang")
public class BarangController {

    @Autowired
    private BarangService barangService;

    @PostMapping
    public ResponseEntity<ApiResponse> getAllBarang(@RequestBody BarangFilterRequest filterRequest) {
        try {
            List<Barang> filteredBarang = barangService.getFilteredBarang(filterRequest);

            if (filteredBarang.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse(true, "No matching items found", new ArrayList<>()));
            }

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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error retrieving items: " + e.getMessage(), null));
        }
    }

    @PostMapping("/detail")
    public ResponseEntity<ApiResponse> getBarangDetail(@RequestBody BarangIdRequest request) {
        try {
            Barang barang = barangService.getBarangById(request.getBarangId());

            if (barang == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Barang tidak ditemukan", null));
            }

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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error retrieving item details: " + e.getMessage(), null));
        }
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse> addProduct(@Valid @RequestBody NewBarangRequest dto) {
        try {
            Barang product = new Barang();

            product.setNamaBarang(dto.getNamaBarang());
            product.setDeskripsiBarang(dto.getDeskripsiBarang());
            product.setHarga(dto.getHarga());
            product.setTipeBarang(dto.getTipeBarang());
            product.setImageUrl(dto.getImageUrl());
            product.setStokBarang(dto.getStokBarang());
            product.setCreatedAt(LocalDateTime.now());

            Barang savedProduct = barangService.addProduct(product);

            Map<String, Object> formattedData = new HashMap<>();
            formattedData.put("barang_id", savedProduct.getBarangId());
            formattedData.put("nama_barang", savedProduct.getNamaBarang());
            formattedData.put("deskripsi_barang", savedProduct.getDeskripsiBarang());
            formattedData.put("harga", savedProduct.getHarga());
            formattedData.put("tipe_barang_id", savedProduct.getTipeBarang());
            formattedData.put("image_url", savedProduct.getImageUrl());
            formattedData.put("stock", savedProduct.getStokBarang());

            ApiResponse response = new ApiResponse(true, "Barang baru berhasil ditambahkan", formattedData);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error adding product: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse> deleteBarang(@Valid @RequestBody DeletebyIDRequest barangIdRequest) {
        try {
            boolean isDeleted = barangService.deleteBarang(barangIdRequest.getBarangId());

            if (isDeleted) {
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("barangId", barangIdRequest.getBarangId());

                return ResponseEntity.ok()
                        .body(new ApiResponse(true, "Barang telah dihapus", responseData));
            } else {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Barang tidak ditemukan"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error deleting product: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update/detail")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse> updateBarang(@Valid @RequestBody UpdateBarangRequest request) {
        try {
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error updating product: " + e.getMessage(), null));
        }
    }
}