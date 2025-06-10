package com.example.rest_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NewBarangRequest {

    @NotBlank(message = "Nama barang tidak boleh kosong")
    private String namaBarang;

    private String deskripsiBarang;

    @NotNull(message = "Harga harus diisi")
    @Min(value = 0, message = "Harga tidak boleh negatif")
    private Double harga;

    @NotBlank(message = "Tipe barang tidak boleh kosong")
    private String tipeBarang;

    private String imageUrl;

    @NotNull(message = "Stok barang harus diisi")
    @Min(value = 0, message = "Stok barang tidak boleh negatif")
    private Integer stokBarang;

    // Getters dan Setters

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getDeskripsiBarang() {
        return deskripsiBarang;
    }

    public void setDeskripsiBarang(String deskripsiBarang) {
        this.deskripsiBarang = deskripsiBarang;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public String getTipeBarang() {
        return tipeBarang;
    }

    public void setTipeBarang(String tipeBarang) {
        this.tipeBarang = tipeBarang;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStokBarang() {
        return stokBarang;
    }

    public void setStokBarang(Integer stokBarang) {
        this.stokBarang = stokBarang;
    }
}
