package com.example.rest_service.controller;

import com.example.rest_service.dto.ListOrderResponse;
import com.example.rest_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Endpoint buat dapetin list order aktif user
    @GetMapping("/active")
    public ListOrderResponse getActiveOrders(@RequestParam("user_id") int userId) {
        return orderService.getKeranjangByUserId(userId);
    }

    // Endpoint buat hapus satu order by id
    @DeleteMapping("/{order_id}")
    public void deleteOrderById(@PathVariable("order_id") int orderId, @RequestParam("user_id") int userId) {
        orderService.deleteOrder(orderId, userId);
    }
}
