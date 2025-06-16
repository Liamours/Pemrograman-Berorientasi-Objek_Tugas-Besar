package com.example.rest_service.repository;

import com.example.rest_service.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

public interface BarangRepository extends JpaRepository<Barang, Integer> {

    List<Barang> findByNamaBarangContainingIgnoreCase(String namaBarang);

    List<Barang> findByTipeContainingIgnoreCase(String tipeBarang);

    Optional<Barang> findById(Integer barangId);

    List<Barang> findByNamaBarangContainingIgnoreCaseAndTipeContainingIgnoreCase(String namaBarang, String tipeBarang);
}
