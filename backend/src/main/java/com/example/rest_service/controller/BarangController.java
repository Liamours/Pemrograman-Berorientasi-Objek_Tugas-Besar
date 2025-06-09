package com.example.rest_service.controller;

import com.example.rest_service.model.Barang;
import com.example.rest_service.service.BarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
