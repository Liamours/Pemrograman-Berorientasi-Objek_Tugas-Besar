package com.example.rest_service.service;

import com.example.rest_service.model.Barang;
import com.example.rest_service.repository.BarangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.rest_service.dto.RequestBarangFilter;
import java.util.List;

@Service
public class BarangService {

    @Autowired
    private BarangRepository barangRepository;

    // Method to filter barang based on the provided filter
    public List<Barang> getBarangWithFilters(RequestBarangFilter filter) {
        // If the filter is empty (both fields are null or empty), return all barang
        if (filter.getNamaBarang() == null && filter.getTipeBarang() == null) {
            return barangRepository.findAll();
        }

        // If both filters are provided, apply both filters
        if (filter.getNamaBarang() != null && filter.getTipeBarang() != null) {
            return barangRepository.findByNamaBarangAndTipeBarang(filter.getNamaBarang(), filter.getTipeBarang());
        }

        // If only 'namaBarang' is provided, filter by 'namaBarang' only
        if (filter.getNamaBarang() != null) {
            return barangRepository.findByNamaBarang(filter.getNamaBarang());
        }
        // If only 'tipeBarang' is provided, filter by 'tipeBarang' only
        return barangRepository.findByTipeBarang(filter.getTipeBarang());
    }

    public Barang addProduct(Barang product) {
        return barangRepository.save(product);
    }
}