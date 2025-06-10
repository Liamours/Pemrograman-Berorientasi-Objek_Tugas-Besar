package com.example.rest_service.repository;

import com.example.rest_service.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarangRepository extends JpaRepository<Barang, Integer> {
}
