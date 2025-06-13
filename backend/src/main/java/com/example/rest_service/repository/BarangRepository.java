package com.example.rest_service.repository;

import com.example.rest_service.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BarangRepository extends JpaRepository<Barang, Integer> {

    // Cari barang yang mengandung nama tertentu
    List<Barang> findByNamaBarangContainingIgnoreCase(String namaBarang);

    // Cari barang yang mengandung tipe tertentu
    List<Barang> findByTipeContainingIgnoreCase(String tipeBarang);

    Optional<Barang> findById(Integer barangId);
    // Cari barang berdasarkan nama dan tipe
    List<Barang> findByNamaBarangContainingIgnoreCaseAndTipeContainingIgnoreCase(String namaBarang, String tipeBarang);
}
