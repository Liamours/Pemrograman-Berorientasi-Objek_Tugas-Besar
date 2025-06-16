package com.example.rest_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class ClientDetail {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private boolean ismember = false;

    @Lob
    @Column(nullable = true)
    private String alamat;

    public ClientDetail() {}

    public ClientDetail(boolean ismember, String alamat) {
        this.ismember = ismember;
        this.alamat = alamat;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public boolean isIsmember() { return ismember; }
    public void setIsmember(boolean ismember) { this.ismember = ismember; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
}