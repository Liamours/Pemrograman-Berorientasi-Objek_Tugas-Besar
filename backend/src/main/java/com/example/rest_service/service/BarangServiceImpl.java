package com.example.rest_service.service;

import com.example.rest_service.dto.*;
import com.example.rest_service.model.Barang;
import com.example.rest_service.repository.BarangRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BarangServiceImpl implements BarangService {

    @Autowired
    private BarangRepository barangRepository;

    @Override
    public List<Barang> getAllBarang() {
        return barangRepository.findAll();
    }

    @Override
    public Barang addProduct(Barang product) {
        return barangRepository.save(product);
    }

    @Override
    public Barang getBarangById(Integer barangId) {
        return barangRepository.findById(barangId).orElse(null);
    }

    @Override
    public boolean deleteBarang(Integer barangId) {
        Barang barang = barangRepository.findById(barangId).orElse(null);
        if (barang != null) {
            barangRepository.delete(barang);
            return true;
        }
        return false;
    }

    @Override
    public Barang updateBarang(@Valid UpdateBarangRequest barang) {
        Optional<Barang> optionalBarang = barangRepository.findById(barang.getBarangId());
        if (optionalBarang.isPresent()) {
            Barang existingBarang = optionalBarang.get();
            existingBarang.setNamaBarang(barang.getNamaBarang());
            existingBarang.setDeskripsiBarang(barang.getDeskripsiBarang());
            existingBarang.setHarga(barang.getHarga());
            existingBarang.setTipeBarang(barang.getTipeBarangId());
            existingBarang.setImageUrl(barang.getImageUrl());
            existingBarang.setStokBarang(barang.getStokBarang());
            return barangRepository.save(existingBarang);
        }
        return null;
    }

    @Override
    public Barang updateStock(@Valid UpdateStockRequest request) {
        Optional<Barang> optionalBarang = barangRepository.findById(request.getBarangId());
        if (optionalBarang.isPresent()) {
            Barang existingBarang = optionalBarang.get();
            existingBarang.setStokBarang(request.getStokBarang());
            return barangRepository.save(existingBarang);
        }
        return null;
    }

    @Override
    public List<Barang> getBarangByFilter(BarangFilterRequest filterRequest) {
        String nama = filterRequest.getNamaBarang();
        String kategori = filterRequest.getTipeBarang();

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
}
