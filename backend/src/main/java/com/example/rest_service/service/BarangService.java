package com.example.rest_service.service;

import com.example.rest_service.model.Barang;
import com.example.rest_service.repository.BarangRepository;
import jakarta.transaction.Transactional;
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
    public Barang getBarangById(Integer barangId) {
        return barangRepository.findById(barangId).orElse(null);
    }

    @Transactional
    public boolean deleteBarang(Integer barangId) {
        Barang barang = barangRepository.findById(barangId).orElse(null);
        if (barang != null) {
            // Save Barang if it's a transient object
            if (barang.getBarangId() == null) {
                barangRepository.save(barang);
            }

            barangRepository.delete(barang); // Now perform the delete
            return true;
        }
        return false;
    }

}
