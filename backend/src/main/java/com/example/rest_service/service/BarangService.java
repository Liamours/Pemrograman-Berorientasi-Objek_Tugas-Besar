package com.example.rest_service.service;

import com.example.rest_service.dto.UpdateBarangRequest;
import com.example.rest_service.dto.BarangFilterRequest;
import com.example.rest_service.model.Barang;
import com.example.rest_service.repository.BarangRepository;
import java.util.Optional;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
            if (barang.getBarangId() == null) {
                barangRepository.save(barang);
            }

            barangRepository.delete(barang);
            return true;
        }
        return false;
    }

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
        } else {
            return null;
        }
    }

    public List<Barang> getFilteredBarang(BarangFilterRequest filterRequest) {
        List<Barang> allBarang = barangRepository.findAll();

        if ((filterRequest.getNamaBarang() == null || filterRequest.getNamaBarang().isEmpty()) &&
                (filterRequest.getTipeBarang() == null || filterRequest.getTipeBarang().isEmpty())) {
            return allBarang;
        }

        return allBarang.stream()
                .filter(barang -> filterByName(barang, filterRequest.getNamaBarang()))
                .filter(barang -> filterByCategory(barang, filterRequest.getTipeBarang()))
                .collect(Collectors.toList());
    }

    private boolean filterByName(Barang barang, String namaBarang) {
        if (namaBarang == null || namaBarang.isEmpty()) {
            return true;
        }
        return barang.getNamaBarang().toLowerCase().contains(namaBarang.toLowerCase());
    }

    private boolean filterByCategory(Barang barang, String tipeBarang) {
        if (tipeBarang == null || tipeBarang.isEmpty()) {
            return true;
        }
        return barang.getTipeBarang().toLowerCase().contains(tipeBarang.toLowerCase());
    }
}
