package com.example.rest_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class UpdateBarangRequest {
    @NotNull(message = "Barang ID cannot be null")
    @JsonProperty("barang_id")
    private Integer barangId;

    @NotBlank(message = "Nama Barang cannot be blank")
    @JsonProperty("nama_barang")
    private String namaBarang;

    @JsonProperty("deskripsi_barang")
    private String deskripsiBarang;

    @NotNull(message = "Harga cannot be null")
    @Min(value = 1, message = "Harga must be greater than 0")
    private Double harga;

    @NotBlank(message = "Tipe barang tidak boleh kosong")
    @Pattern(regexp = "^(Makanan|Minuman|Hygine)$", message = "Tipe barang hanya boleh 'Makanan', 'Minuman', atau 'Hygine'")
    @JsonProperty("tipe_barang")
    private String tipeBarangId;

    @JsonProperty("image_url")
    private String imageUrl;

    @NotNull(message = "Stok Barang cannot be null")
    @JsonProperty("stok_barang")
    private Integer stokBarang;

    // Getters and Setters
    public Integer getBarangId() {
        return barangId;
    }

    public void setBarangId(Integer barangId) {
        this.barangId = barangId;
    }

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

    public String getTipeBarangId() {
        return tipeBarangId;
    }

    public void setTipeBarangId(String tipeBarangId) {
        this.tipeBarangId = tipeBarangId;
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
