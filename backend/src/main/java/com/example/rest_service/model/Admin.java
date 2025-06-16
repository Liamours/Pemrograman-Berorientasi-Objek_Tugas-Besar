package com.example.rest_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(String namaUser, String email, String password) {
        super(namaUser, email, password);
        this.setPeran(Role.Admin);
    }
}