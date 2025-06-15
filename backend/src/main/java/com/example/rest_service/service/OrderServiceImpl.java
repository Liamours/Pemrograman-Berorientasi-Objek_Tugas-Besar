// src/main/java/com/example/rest_service/service/impl/OrderServiceImpl.java
package com.example.rest_service.service;

import com.example.rest_service.dto.OrderDTO;
import com.example.rest_service.model.*;
import com.example.rest_service.repository.*;
import com.example.rest_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private BarangRepository barangRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private KeranjangRepository keranjangRepository;

    @Override
    public OrderDTO addOrder(Long userId, Integer barangId, Integer jumlahBarang, String alamatTujuan) {
        Barang barang = barangRepository.findById(barangId)
                .orElseThrow(() -> new RuntimeException("Barang tidak ditemukan"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        Keranjang keranjang = keranjangRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Keranjang tidak ditemukan"));

        Order order = new Order();
        order.setBarang(barang);
        order.setJumlahBarang(jumlahBarang);
        order.setHargaPerUnit(barang.getHarga());
        order.setTanggalOrder(LocalDateTime.now());
        order.setAlamatTujuan(alamatTujuan);
        order.setStatusOrder(StatusOrder.Pending_Client);
        order.setUser(user);
        order.setKeranjang(keranjang);

        orderRepository.save(order);

        return toDTO(order);
    }

    @Override
    public OrderDTO getOrderById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order tidak ditemukan"));
        return toDTO(order);
    }

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
