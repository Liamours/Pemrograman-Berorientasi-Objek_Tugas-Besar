// src/main/java/com/example/rest_service/model/Keranjang.java
package com.example.rest_service.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "keranjang")
public class Keranjang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keranjang_id")
    private Integer keranjangId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",unique = true)
    private User user;


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

}
