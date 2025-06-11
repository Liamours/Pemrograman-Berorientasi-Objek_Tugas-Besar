package com.example.rest_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class UpdateStockRequest {

    @JsonProperty("barang_id")
    private Integer barangId;

    @NotNull(message = "Stock barang tidak boleh kosong")
    @JsonProperty("stok_barang")
    private Integer stokBarang;

    // Getters and Setters
    public Integer getBarangId() {
        return barangId;
    }

    public void setBarangId(Integer barangId) {
        this.barangId = barangId;
    }

    public Integer getStokBarang() {
        return stokBarang;
    }

    public void setStokBarang(Integer stokBarang) {
        this.stokBarang = stokBarang;
    }
}
