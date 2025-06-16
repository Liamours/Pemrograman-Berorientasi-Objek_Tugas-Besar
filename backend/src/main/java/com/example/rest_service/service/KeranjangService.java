package com.example.rest_service.service;

import com.example.rest_service.dto.CartDTO;
import com.example.rest_service.dto.OrderDTO;

import java.util.List;

public interface KeranjangService {
    CartDTO getCartByUser(Long userId);
    OrderDTO updateOrder(Integer orderId, Integer jumlahBarang);
    void removeOrder(Integer orderId);
    CartDTO checkout(Long userId, List<Integer> orderIds);
    List<OrderDTO> getOrdersInCart(Long userId); // Pastikan metode ini ada
}