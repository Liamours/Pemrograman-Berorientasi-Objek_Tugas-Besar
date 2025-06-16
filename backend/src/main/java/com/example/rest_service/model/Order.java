// src/main/java/com/example/rest_service/model/Order.java
package com.example.rest_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @OneToOne
    @JoinColumn(name = "barang_id", referencedColumnName = "barang_id")
    private Barang barang;

    @Column(name = "jumlah_barang")
    private Integer jumlahBarang;

    @Column(name = "harga_per_unit")
    private Double hargaPerUnit;

    @Column(name = "tanggal_order")
    private LocalDateTime tanggalOrder;

    @Column(name = "alamat_tujuan")
    private String alamatTujuan;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_order")
    private StatusOrder statusOrder;

    @ManyToOne
    @JoinColumn(name = "keranjang_id")
    private Keranjang keranjang;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // getter & setter
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Barang getBarang() { return barang; }
    public void setBarang(Barang barang) { this.barang = barang; }

    public Integer getJumlahBarang() { return jumlahBarang; }
    public void setJumlahBarang(Integer jumlahBarang) { this.jumlahBarang = jumlahBarang; }

    public Double getHargaPerUnit() { return hargaPerUnit; }
    public void setHargaPerUnit(Double hargaPerUnit) { this.hargaPerUnit = hargaPerUnit; }

    public LocalDateTime getTanggalOrder() { return tanggalOrder; }
    public void setTanggalOrder(LocalDateTime tanggalOrder) { this.tanggalOrder = tanggalOrder; }

    public String getAlamatTujuan() { return alamatTujuan; }
    public void setAlamatTujuan(String alamatTujuan) { this.alamatTujuan = alamatTujuan; }

    public StatusOrder getStatusOrder() { return statusOrder; }
    public void setStatusOrder(StatusOrder statusOrder) { this.statusOrder = statusOrder; }

    public Keranjang getKeranjang() { return keranjang; }
    public void setKeranjang(Keranjang keranjang) { this.keranjang = keranjang; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
