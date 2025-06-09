package com.example.rest_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "barang")
public class Barang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer barangId;

    private String namaBarang;

    @Lob
    private String deskripsiBarang;

    private BigDecimal harga;

    private String tipeBarang;

    private String imageUrl;

    private LocalDateTime createdAt;

    public Barang() {}

    public Integer getBarangId() { return barangId; }
    public void setBarangId(Integer barangId) { this.barangId = barangId; }

    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }

    public String getDeskripsiBarang() { return deskripsiBarang; }
    public void setDeskripsiBarang(String deskripsiBarang) { this.deskripsiBarang = deskripsiBarang; }

    public BigDecimal getHarga() { return harga; }
    public void setHarga(BigDecimal harga) { this.harga = harga; }

    public String getTipeBarang() { return tipeBarang; }
    public void setTipeBarang(String tipeBarang) { this.tipeBarang = tipeBarang; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
