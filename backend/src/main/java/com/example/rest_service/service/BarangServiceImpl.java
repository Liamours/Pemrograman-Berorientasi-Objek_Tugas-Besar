package com.example.rest_service.service;

import com.example.rest_service.model.Barang;
import com.example.rest_service.repository.BarangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarangServiceImpl implements BarangService {

    @Autowired
    private BarangRepository barangRepository;

    @Override
    public List<Barang> getBarangByFilter(String nama, String kategori) {
        if (nama != null && kategori != null) {
            return barangRepository.findByNamaBarangContainingIgnoreCaseAndTipeContainingIgnoreCase(nama, kategori);
        } else if (nama != null) {
            return barangRepository.findByNamaBarangContainingIgnoreCase(nama);
        } else if (kategori != null) {
            return barangRepository.findByTipeContainingIgnoreCase(kategori);
        } else {
            return barangRepository.findAll();
        }
    }


    @Override
    public List<Barang> getAllBarang() {
        return barangRepository.findAll();
    }

    @Override
    public Barang addProduct(Barang barang) {
        return barangRepository.save(barang);
    }
}
