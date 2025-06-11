package com.example.rest_service.service;

import com.example.rest_service.model.Barang;
import java.util.List;

public interface BarangService {
    List<Barang> getBarangByFilter(String nama, String kategori);
    List<Barang> getAllBarang(); // <<== tambahin ini!
    Barang addProduct(Barang barang); // biar method addProduct juga ada
}
