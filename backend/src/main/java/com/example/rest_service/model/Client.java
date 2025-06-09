package com.example.rest_service.model;

import jakarta.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "client")
public class Client implements Serializable {

    @Id
    private Integer userId;
    private Boolean ismember;
    private String alamat;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsmember() {
        return ismember;
    }

    public void setIsmember(Boolean ismember) {
        this.ismember = ismember;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
