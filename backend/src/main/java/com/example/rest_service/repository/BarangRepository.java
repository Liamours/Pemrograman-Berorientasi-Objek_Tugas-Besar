package com.example.rest_service.repository;

import com.example.rest_service.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BarangRepository extends JpaRepository<Barang, Integer> {
    @Query("SELECT b FROM Barang b")
    ArrayList<Barang> findAllBarang();
    // Mengambil semua barang tanpa keranjang atau order
}
