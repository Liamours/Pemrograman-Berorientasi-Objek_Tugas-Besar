// src/main/java/com/example/rest_service/controller/OrderController.java
package com.example.rest_service.controller;

import com.example.rest_service.dto.OrderDTO;
import com.example.rest_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired private OrderService orderService;

    @PostMapping("/add")
    public OrderDTO addOrder(
            @RequestHeader("Authorization") String token,
            @RequestParam Integer barangId,
            @RequestParam Integer jumlahBarang,
            @RequestParam String alamatTujuan
    ) {
        Integer userId = validateToken(token);
        return orderService.addOrder(Long.valueOf(userId), barangId, jumlahBarang, alamatTujuan);
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrderById(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId);
    }

    private Integer validateToken(String token) {
        // TODO: Ganti dengan validasi token sesungguhnya
        return Integer.parseInt(token);
    }
}
