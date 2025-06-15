// src/main/java/com/example/rest_service/controller/AdminOrderController.java
package com.example.rest_service.controller;

import com.example.rest_service.dto.OrderDTO;
import com.example.rest_service.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    // Endpoint untuk mendapatkan semua order dengan status Pending_Admin
    @GetMapping("/pending")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<OrderDTO>> getPendingOrders() {
        return ResponseEntity.ok(adminOrderService.getPendingOrders());
    }

    // Endpoint untuk menyetujui order
    @PostMapping("/approve/{orderId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<OrderDTO> approveOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(adminOrderService.approveOrder(orderId));
    }
}