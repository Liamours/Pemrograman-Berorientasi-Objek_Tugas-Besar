package com.example.backend.controller;

import com.example.backend.model.Barang;
import com.example.backend.repository.BarangRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barang")
public class BarangController {

    private final BarangRepository barangRepository;

    public BarangController(BarangRepository barangRepository) {
        this.barangRepository = barangRepository;
    }

    @GetMapping("/galeri")
    public List<Barang> getGaleriBarang() {
        return barangRepository.findAll();
    }
}
