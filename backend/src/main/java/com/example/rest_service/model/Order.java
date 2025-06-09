package com.example.rest_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "barang_id", nullable = false)
    private Barang barang;

    private Integer jumlahBarang;
    private Double hargaPerUnit;
    private LocalDateTime tanggalOrder;
    private String alamatTujuan;

    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    @ManyToOne
    @JoinColumn(name = "keranjang_id")
    private Keranjang keranjang;


    public enum StatusOrder {
        Checkout,
        belum
    }

    // Getters and setters
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public Integer getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(Integer jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public Double getHargaPerUnit() {
        return hargaPerUnit;
    }

    public void setHargaPerUnit(Double hargaPerUnit) {
        this.hargaPerUnit = hargaPerUnit;
    }

    public LocalDateTime getTanggalOrder() {
        return tanggalOrder;
    }

    public void setTanggalOrder(LocalDateTime tanggalOrder) {
        this.tanggalOrder = tanggalOrder;
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public void setAlamatTujuan(String alamatTujuan) {
        this.alamatTujuan = alamatTujuan;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public Keranjang getKeranjang() {
        return keranjang;
    }

    public void setKeranjang(Keranjang keranjang) {
        this.keranjang = keranjang;
    }
}

