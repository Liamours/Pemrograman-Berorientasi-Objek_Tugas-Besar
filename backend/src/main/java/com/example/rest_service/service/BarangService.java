package com.example.rest_service.service;

import com.example.rest_service.dto.*;
import com.example.rest_service.model.Barang;
import jakarta.validation.Valid;

import java.util.List;

public interface BarangService {
    List<Barang> getBarangByFilter(BarangFilterRequest filterRequest);
    List<Barang> getAllBarang();
    Barang getBarangById(Integer id);
    Barang addProduct(Barang barang);
    Barang updateBarang(@Valid UpdateBarangRequest request);
    Barang updateStock(@Valid UpdateStockRequest request);
    boolean deleteBarang(Integer id);
}
