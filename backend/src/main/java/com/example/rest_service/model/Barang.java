package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "barang")
public class Barang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;
    private int stok;
    private double harga;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
}
