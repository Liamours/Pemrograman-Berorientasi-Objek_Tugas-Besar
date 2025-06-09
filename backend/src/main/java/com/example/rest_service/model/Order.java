package com.example.rest_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`") // pakai backtick karena 'order' adalah reserved word
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime tanggalOrder;

    private BigDecimal totalHarga;

    @Lob
    private String alamatTujuan;

    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder = StatusOrder.Pending_Client;

    public enum StatusOrder {
        Pending_Client,
        Pending_Admin,
        Done
    }

    public Order() {}

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getTanggalOrder() { return tanggalOrder; }
    public void setTanggalOrder(LocalDateTime tanggalOrder) { this.tanggalOrder = tanggalOrder; }

    public BigDecimal getTotalHarga() { return totalHarga; }
    public void setTotalHarga(BigDecimal totalHarga) { this.totalHarga = totalHarga; }

    public String getAlamatTujuan() { return alamatTujuan; }
    public void setAlamatTujuan(String alamatTujuan) { this.alamatTujuan = alamatTujuan; }

    public StatusOrder getStatusOrder() { return statusOrder; }
    public void setStatusOrder(StatusOrder statusOrder) { this.statusOrder = statusOrder; }
}
