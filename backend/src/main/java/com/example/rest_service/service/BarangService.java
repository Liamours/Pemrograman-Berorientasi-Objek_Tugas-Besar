package com.example.rest_service.service;

import com.example.rest_service.dto.UpdateBarangRequest;
import com.example.rest_service.dto.UpdateStockRequest;
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
            // Save Barang if it's a transient object
            if (barang.getBarangId() == null) {
                barangRepository.save(barang);
            }

            barangRepository.delete(barang); // Now perform the delete
            return true;
        }
        return false;
    }

    public Barang updateBarang(@Valid UpdateBarangRequest barang) {
        // 1. Find the Barang by ID
        Optional<Barang> optionalBarang = barangRepository.findById(barang.getBarangId());

        if (optionalBarang.isPresent()) {
            Barang existingBarang = optionalBarang.get();

            // 2. Update the fields of Barang based on the input entity
            existingBarang.setNamaBarang(barang.getNamaBarang());
            existingBarang.setDeskripsiBarang(barang.getDeskripsiBarang());
            existingBarang.setHarga(barang.getHarga());
            existingBarang.setTipeBarang(barang.getTipeBarangId());
            existingBarang.setImageUrl(barang.getImageUrl());
            existingBarang.setStokBarang(barang.getStokBarang());

            // 3. Save the updated Barang back to the repository
            return barangRepository.save(existingBarang);
        } else {
            return null; // Return null if the Barang with given ID does not exist
        }
    }

    // Update only stock
    public Barang updateStock(@Valid UpdateStockRequest request) {
        // 1. Find the Barang by ID
        Optional<Barang> optionalBarang = barangRepository.findById(request.getBarangId());

        // 2. Check if the Barang exists
        if (optionalBarang.isPresent()) {
            Barang existingBarang = optionalBarang.get();

            // 3. Update the stock value
            existingBarang.setStokBarang(request.getStokBarang());

            // 4. Save the updated Barang with the new stock value
            return barangRepository.save(existingBarang);
        } else {
            return null; // Return null if the Barang with given ID does not exist
        }
    }

    public List<Barang> getFilteredBarang(BarangFilterRequest filterRequest) {
        // Ambil semua barang dari database
        List<Barang> allBarang = barangRepository.findAll();

        // Jika keduanya kosong, maka tidak ada filter, kembalikan semua barang
        if ((filterRequest.getNamaBarang() == null || filterRequest.getNamaBarang().isEmpty()) &&
                (filterRequest.getTipeBarang() == null || filterRequest.getTipeBarang().isEmpty())) {
            return allBarang;
        }

        // Terapkan filter berdasarkan nama_barang dan tipe_barang
        return allBarang.stream()
                .filter(barang -> filterByName(barang, filterRequest.getNamaBarang()))
                .filter(barang -> filterByCategory(barang, filterRequest.getTipeBarang()))
                .collect(Collectors.toList());
    }

    private boolean filterByName(Barang barang, String namaBarang) {
        if (namaBarang == null || namaBarang.isEmpty()) {
            return true;  // Jika nama_barang kosong, abaikan filter untuk nama
        }
        return barang.getNamaBarang().toLowerCase().contains(namaBarang.toLowerCase());
    }

    private boolean filterByCategory(Barang barang, String tipeBarang) {
        if (tipeBarang == null || tipeBarang.isEmpty()) {
            return true;  // Jika tipe_barang kosong, abaikan filter untuk kategori
        }
        return barang.getTipeBarang().toLowerCase().contains(tipeBarang.toLowerCase());
    }
}
