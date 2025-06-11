package com.example.rest_service.repository;

import com.example.rest_service.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarangRepository extends JpaRepository<Barang, Integer> {

    // Cari barang yang mengandung nama tertentu
    List<Barang> findByNamaBarangContainingIgnoreCase(String namaBarang);

    // Cari barang yang mengandung tipe tertentu
    List<Barang> findByTipeContainingIgnoreCase(String tipeBarang);

    // Cari barang berdasarkan nama dan tipe
    List<Barang> findByNamaBarangContainingIgnoreCaseAndTipeContainingIgnoreCase(String namaBarang, String tipeBarang);
}
