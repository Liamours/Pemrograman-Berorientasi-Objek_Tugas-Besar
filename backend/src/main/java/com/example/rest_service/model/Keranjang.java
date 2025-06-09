package com.example.rest_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "keranjang")
public class Keranjang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer keranjangId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "barang_id")
    private Barang barang;

    @Column(nullable = false)
    private Integer jumlahBarang;

    private LocalDateTime waktuDitambahkan;

    public Keranjang() {}

    public Integer getKeranjangId() { return keranjangId; }
    public void setKeranjangId(Integer keranjangId) { this.keranjangId = keranjangId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Barang getBarang() { return barang; }
    public void setBarang(Barang barang) { this.barang = barang; }

    public Integer getJumlahBarang() { return jumlahBarang; }
    public void setJumlahBarang(Integer jumlahBarang) { this.jumlahBarang = jumlahBarang; }

    public LocalDateTime getWaktuDitambahkan() { return waktuDitambahkan; }
    public void setWaktuDitambahkan(LocalDateTime waktuDitambahkan) { this.waktuDitambahkan = waktuDitambahkan; }
}
