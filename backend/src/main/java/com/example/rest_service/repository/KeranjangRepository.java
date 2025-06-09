package com.example.rest_service.repository;

import com.example.rest_service.model.Keranjang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeranjangRepository extends JpaRepository<Keranjang, Integer> {
    // Custom queries for Keranjang can be added here
}
