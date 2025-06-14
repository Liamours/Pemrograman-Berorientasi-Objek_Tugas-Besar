package com.example.backend.repository;

import com.example.backend.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarangRepository extends JpaRepository<Barang, Long> {
}
