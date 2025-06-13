package com.example.rest_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "keranjang")
public class Keranjang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keranjang_id")
    private Integer keranjangId;

    // Correcting JoinColumn to reference the 'user_id' column
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") // References the user_id from User entity
    private User user;

    @OneToMany(mappedBy = "keranjang")
    private List<Order> orders;

    @Column(name = "waktu_ditambahkan")
    private LocalDateTime waktuDitambahkan;

    // Getters and setters
    public Integer getKeranjangId() {
        return keranjangId;
    }

    public void setKeranjangId(Integer keranjangId) {
        this.keranjangId = keranjangId;
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