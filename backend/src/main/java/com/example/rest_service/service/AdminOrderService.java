// src/main/java/com/example/rest_service/service/AdminOrderService.java
package com.example.rest_service.service;

import com.example.rest_service.dto.OrderDTO;
import com.example.rest_service.model.Order;
import com.example.rest_service.model.StatusOrder;
import com.example.rest_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminOrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Mendapatkan semua order dengan status Pending_Admin
    public List<OrderDTO> getPendingOrders() {
        return orderRepository.findByStatusOrder(StatusOrder.Pending_Admin)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Menyetujui order (mengubah status menjadi Done)
    public OrderDTO approveOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getStatusOrder().equals(StatusOrder.Pending_Admin)) {
            throw new RuntimeException("Order cannot be approved as it is not in Pending_Admin status");
        }
        order.setStatusOrder(StatusOrder.Done);
        return toDTO(orderRepository.save(order));
    }

    // Helper method untuk mengonversi Order ke OrderDTO
    private OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setBarangId(order.getBarang().getBarangId());
        dto.setJumlahBarang(order.getJumlahBarang());
        dto.setHargaPerUnit(order.getHargaPerUnit());
        dto.setTanggalOrder(order.getTanggalOrder());
        dto.setAlamatTujuan(order.getAlamatTujuan());
        dto.setStatusOrder(order.getStatusOrder().name());
        return dto;
    }
}