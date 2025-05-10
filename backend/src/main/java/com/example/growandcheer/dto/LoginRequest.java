package com.example.growandcheer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginRequest {
    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    // hanya huruf, angka, titik, strip dan @ dibolehkan
    @Pattern(
            regexp = "^[A-Za-z0-9@.]+$",
            message = "Email hanya boleh mengandung A–Z, a–z, 0–9, @ dan ."
    )
    private String email;

    @NotBlank
    private String password;

    // getters & setters …

    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }


}
