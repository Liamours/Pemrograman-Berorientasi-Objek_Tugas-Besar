package com.example.rest_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "keranjang")
public class Keranjang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // Pastikan ada kolom id
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "keranjang")
    private List<Order> orders;

    private LocalDateTime waktuDitambahkan;

    // Getters and setters
    public int getKeranjangId() {
        return id;
    }

    public void setKeranjangId(int keranjangId) {
        this.id = keranjangId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getWaktuDitambahkan() {
        return waktuDitambahkan;
    }

    public void setWaktuDitambahkan(LocalDateTime waktuDitambahkan) {
        this.waktuDitambahkan = waktuDitambahkan;
    }
}
