package com.example.rest_service.service;

import com.example.rest_service.dto.CartDTO;
import com.example.rest_service.dto.OrderDTO;
import com.example.rest_service.model.*;
import com.example.rest_service.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeranjangServiceImpl implements KeranjangService {
    @Autowired private KeranjangRepository krRepo;
    @Autowired private OrderRepository orRepo;
    @Autowired private EntityManager entityManager;
    @Override
    public CartDTO getCartByUser(Long userId) {
        Keranjang k = krRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Keranjang not found"));

        List<OrderDTO> dtos = orRepo.findByKeranjangKeranjangId(k.getKeranjangId())
                .stream()
                .filter(order -> order.getStatusOrder() == StatusOrder.Pending)
                .map(this::toDTO)
                .collect(Collectors.toList());

        BigDecimal total = dtos.stream()
                .map(o -> BigDecimal.valueOf(o.getHargaPerUnit())
                        .multiply(BigDecimal.valueOf(o.getJumlahBarang())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartDTO cart = new CartDTO();
        cart.setOrders(dtos);
        cart.setTotal(total.doubleValue());
        return cart;
    }

    @Override
    public OrderDTO updateOrder(Integer orderId, Integer jumlahBarang) {
        Order o = orRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        o.setJumlahBarang(jumlahBarang);
        return toDTO(orRepo.save(o));
    }

    @Override
    @Transactional
    public void removeOrder(Integer orderId) {
        try {

            if (!orRepo.existsById(orderId)) {
                throw new RuntimeException("Order not found");
            }


            orRepo.executeDeleteOrder(orderId);


            entityManager.flush();
            entityManager.clear();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete order: " + e.getMessage());
        }
    }
    @Override
    public List<OrderDTO> checkout(Long userId, List<Integer> orderIds) {
        Keranjang k = krRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Keranjang not found"));


        List<Order> orders = orRepo.findByKeranjangKeranjangId(k.getKeranjangId())
                .stream()
                .filter(order -> order.getStatusOrder() == StatusOrder.Pending)
                .filter(order -> orderIds.contains(order.getOrderId()))
                .collect(Collectors.toList());

        if (orders.isEmpty()) {
            throw new RuntimeException("No valid orders found for checkout");
        }


        orders.forEach(order -> {
            order.setStatusOrder(StatusOrder.Done);
            orRepo.save(order);
        });


        return orders.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersInCart(Long userId) {
        Keranjang k = krRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Keranjang not found"));

        return orRepo.findByKeranjangKeranjangId(k.getKeranjangId())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO toDTO(Order o) {
        OrderDTO d = new OrderDTO();
        d.setOrderId(o.getOrderId());
        d.setBarangId(o.getBarang().getBarangId());
        d.setJumlahBarang(o.getJumlahBarang());
        d.setHargaPerUnit(o.getHargaPerUnit());
        d.setTanggalOrder(o.getTanggalOrder());
        d.setAlamatTujuan(o.getAlamatTujuan());
        d.setStatusOrder(o.getStatusOrder().name());
        return d;
    }
}