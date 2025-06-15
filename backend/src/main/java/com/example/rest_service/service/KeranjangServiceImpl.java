package com.example.rest_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rest_service.model.Keranjang;
import com.example.rest_service.model.User;
import com.example.rest_service.repository.KeranjangRepository;

@Service
public class KeranjangServiceImpl implements KeranjangService {

    @Autowired
    private KeranjangRepository keranjangRepository;

    @Override
    public Keranjang getKeranjangByUser(User user) {
        return keranjangRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Keranjang untuk user ini nggak ditemukan."));
    }
}
