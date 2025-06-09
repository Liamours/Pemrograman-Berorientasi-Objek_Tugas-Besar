package com.example.rest_service.repository;

import com.example.rest_service.model.Keranjang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeranjangRepository extends JpaRepository<Keranjang, Integer> {
}
