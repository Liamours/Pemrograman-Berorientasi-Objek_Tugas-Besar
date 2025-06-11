package com.example.rest_service.service;

import com.example.rest_service.model.Keranjang;
import com.example.rest_service.model.User;

public interface KeranjangService {
    Keranjang getKeranjangByUser(User user);
}
