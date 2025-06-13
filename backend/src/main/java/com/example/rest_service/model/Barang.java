package com.example.rest_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "barang")
public class Barang {

    public Barang(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barang_id")
    private Integer barangId;

    @Column(name = "nama_barang")
    private String namaBarang;

    @Column(name = "deskripsi_barang")
    private String deskripsiBarang;

    @Column(name = "harga")
    private Double harga;

    @Column(name = "tipe_barang")
    private String tipe;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "stok_barang")
    private Integer stokBarang;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "barang",cascade = CascadeType.ALL)
    private List<Order> orders;


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

    public String getTipeBarang() {
        return tipe;
    }

    public void setTipeBarang(String tipeBarang) {
        this.tipe = tipeBarang;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}