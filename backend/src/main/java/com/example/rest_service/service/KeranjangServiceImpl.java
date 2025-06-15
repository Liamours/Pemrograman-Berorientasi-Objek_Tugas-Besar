
package com.example.rest_service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rest_service.dto.CartDTO;
import com.example.rest_service.dto.OrderDTO;
import com.example.rest_service.model.Keranjang;
import com.example.rest_service.model.Order;
import com.example.rest_service.model.StatusOrder;
import com.example.rest_service.repository.KeranjangRepository;
import com.example.rest_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeranjangServiceImpl implements KeranjangService {

    @Autowired private KeranjangRepository krRepo;
    @Autowired private OrderRepository orRepo;

    @Override
    public CartDTO getCartByUser(Long userId) {
        Keranjang k = krRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Keranjang not found"));
        List<OrderDTO> dtos = orRepo.findByKeranjangKeranjangId(k.getKeranjangId())
                .stream().map(this::toDTO).collect(Collectors.toList());
        BigDecimal total = dtos.stream()
                .map(o -> BigDecimal.valueOf(o.getHargaPerUnit())
                        .multiply(BigDecimal.valueOf(o.getJumlahBarang())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        CartDTO cart = new CartDTO();
        cart.setOrders(dtos);
        cart.setTotalPrice(total);
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
    public void removeOrder(Integer orderId) {
        orRepo.deleteById(orderId);
    }

    @Override
    public CartDTO checkout(Long userId, List<Integer> orderIds) {
        CartDTO cart = getCartByUser(userId);
        cart.getOrders().stream()
                .filter(o -> orderIds.contains(o.getOrderId()))
                .forEach(o -> {
                    Order m = orRepo.getOne(o.getOrderId());
                    m.setStatusOrder(StatusOrder.Pending_Admin); // Ubah status menjadi Pending_Admin
                    orRepo.save(m);
                });
        return getCartByUser(userId);
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