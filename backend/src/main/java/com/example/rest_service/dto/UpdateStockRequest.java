package com.example.rest_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class UpdateStockRequest {

    @JsonProperty("barang_id")
    private Integer barang_id;

    @NotNull(message = "Stock barang tidak boleh kosong")
    @JsonProperty("stok_barang")
    private Integer stok_barang;

    // Getters and Setters
    public Integer getBarangId() {
        return barang_id;
    }

    public void setBarangId(Integer barangId) {
        this.barang_id = barangId;
    }

    public Integer getStokBarang() {
        return stok_barang;
    }

    public void setStokBarang(Integer stokBarang) {
        this.stok_barang = stokBarang;
    }
}
