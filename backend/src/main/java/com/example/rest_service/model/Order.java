package com.example.rest_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
public class Order {

    public Order(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "keranjang_id", referencedColumnName = "id")
    private Keranjang keranjang;

    @ManyToOne
    @JoinColumn(name = "barang_id")
    private Barang barang;

    @ManyToOne
    @JoinColumn(name = "user_id") // Tambahan biar bisa pakai findByOrderIdAndUser
    private User user;

    private Integer jumlahBarang;
    private Double hargaPerUnit;
    private Double totalHarga; // Tambahan yang kamu pakai di service
    private LocalDateTime tanggalOrder;
    private String alamatTujuan;

    @Column(name = "status_order")
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    public enum StatusOrder {
        Pending_Client,
        Pending_Admin,
        Done
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Keranjang getKeranjang() {
        return keranjang;
    }

    public void setKeranjang(Keranjang keranjang) {
        this.keranjang = keranjang;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(Double totalHarga) {
        this.totalHarga = totalHarga;
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
}
