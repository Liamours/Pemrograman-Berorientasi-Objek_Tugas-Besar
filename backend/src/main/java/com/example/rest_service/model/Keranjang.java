package com.example.rest_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "keranjang")
public class Keranjang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer keranjangId;

    // @OneToOne
    // @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    // private User user;

    // @ManyToOne
    // @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    // private Order order;

    private LocalDateTime waktuDitambahkan;

    // Getters and setters
    public Integer getKeranjangId() {
        return keranjangId;
    }

    public void setKeranjangId(Integer keranjangId) {
        this.keranjangId = keranjangId;
    }

    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }

    // public Order getOrder() {
    //     return order;
    // }

    // public void setOrder(Order order) {
    //     this.order = order;
    // }

    public LocalDateTime getWaktuDitambahkan() {
        return waktuDitambahkan;
    }

    public void setWaktuDitambahkan(LocalDateTime waktuDitambahkan) {
        this.waktuDitambahkan = waktuDitambahkan;
    }
}
