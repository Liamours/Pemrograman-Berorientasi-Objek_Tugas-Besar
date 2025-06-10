package com.example.rest_service.dto;

public class RequestBarangFilter {
    private String namaBarang;
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