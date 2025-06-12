package com.example.rest_service.controller;

import com.example.rest_service.dto.CheckoutRequest;
import com.example.rest_service.dto.CheckoutResponse;
import com.example.rest_service.dto.ListOrderResponse;
import com.example.rest_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/keranjang")
public class KeranjangController {

    @Autowired
    private OrderService orderService;

    // 1. GET /keranjang?user_id=1
    @GetMapping
    public ListOrderResponse getKeranjang(@RequestParam("user_id") int userId) {
        return orderService.getKeranjangByUserId(userId);
    }

    // 2. POST /keranjang/checkout
    @PostMapping("/checkout")
    public CheckoutResponse checkout(@RequestBody CheckoutRequest request, @RequestParam("user_id") int userId) {
        return orderService.checkout(request, userId);
    }

    // 3. DELETE /keranjang/order?order_id=1&user_id=1
    @DeleteMapping("/order")
    public void deleteOrder(@RequestParam("order_id") int orderId, @RequestParam("user_id") int userId) {
        orderService.deleteOrder(orderId, userId);
    }

    // PUT /keranjang/order
    @PutMapping("/order")
    public void updateOrderJumlah(@RequestParam("order_id") int orderId,
                                  @RequestParam("user_id") int userId,
                                  @RequestParam("jumlah") int jumlah) {
        orderService.updateOrderJumlah(orderId, userId, jumlah);
    }

}