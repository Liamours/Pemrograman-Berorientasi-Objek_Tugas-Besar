package com.example.rest_service.repository;

import com.example.rest_service.model.Keranjang;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KeranjangRepository extends JpaRepository<Keranjang, Integer> {
    Optional<Keranjang> findByUser_Id(Long userId); // Updated method name
}