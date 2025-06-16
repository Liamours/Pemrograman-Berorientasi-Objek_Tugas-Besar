package com.example.rest_service.service;

import com.example.rest_service.dto.OrderDTO;

public interface OrderService {
    OrderDTO addOrder(Long userId, Integer barangId, Integer jumlahBarang, String alamatTujuan);
    OrderDTO getOrderById(Integer orderId);
}
