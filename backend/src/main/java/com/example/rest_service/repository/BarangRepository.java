package com.example.rest_service.repository;

import com.example.rest_service.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BarangRepository extends JpaRepository<Barang, Integer> {
    // Custom query to find barang based on namaBarang and tipeBarang
    List<Barang> findByNamaBarangAndTipeBarang(String namaBarang, String tipeBarang);

    // Custom query to find barang based on namaBarang only
    List<Barang> findByNamaBarang(String namaBarang);

    // Custom query to find barang based on tipeBarang only
    List<Barang> findByTipeBarang(String tipeBarang);
}
