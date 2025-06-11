package com.example.rest_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BarangFilterRequest {
    @JsonProperty("nama_barang")
    private String namaBarang;

    @JsonProperty("tipe_barang")
    private String tipeBarang;

    // Getters and Setters
    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getTipeBarang() {
        return tipeBarang;
    }

    public void setTipeBarang(String tipeBarang) {
        this.tipeBarang = tipeBarang;
    }
}
