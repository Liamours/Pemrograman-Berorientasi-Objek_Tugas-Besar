package com.example.rest_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

public class NewBarangRequest {

    @NotBlank(message = "Nama barang tidak boleh kosong")
    @JsonProperty("nama_barang")
    private String namaBarang;

    @JsonProperty("deskripsi_barang")
    private String deskripsiBarang;

    @NotNull(message = "Harga harus diisi")
    @Min(value = 0, message = "Harga tidak boleh negatif")
    @JsonProperty("harga")
    private Double harga;

    @NotBlank(message = "Tipe barang tidak boleh kosong")
    @Pattern(regexp = "^(Makanan|Minuman|Hygine)$", message = "Tipe barang hanya boleh 'Makanan', 'Minuman', atau 'Hygine'")
    @JsonProperty("tipe_barang")
    private String tipeBarang;

    @JsonProperty("image_url")
    private String imageUrl;

    @NotNull(message = "Stok barang harus diisi")
    @Min(value = 0, message = "Stok barang tidak boleh negatif")
    @JsonProperty("stok_barang")
    private Integer stokBarang;

    // Getters and Setters
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
