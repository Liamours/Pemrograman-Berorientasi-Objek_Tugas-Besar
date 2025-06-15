package com.example.rest_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class DeleteAccountRequest {
    @NotBlank(message = "Email tidak boleh kosong")
    private String password;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
