package com.example.rest_service.service;

import com.example.rest_service.model.Barang;
import com.example.rest_service.repository.BarangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarangService {

    @Autowired
    private BarangRepository barangRepository;

    public List<Barang> getAllBarang() {
        return barangRepository.findAll();
    }

    public Barang addProduct(Barang product) {
        return barangRepository.save(product);
    }
}