// package com.example.backend.dto;

public class CheckoutItemDTO {
    private Long barangId;
    private int jumlah;

    public Long getBarangId() { return barangId; }
    public void setBarangId(Long barangId) { this.barangId = barangId; }

    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
}
