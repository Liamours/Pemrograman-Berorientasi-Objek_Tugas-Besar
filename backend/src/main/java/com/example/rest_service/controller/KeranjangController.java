// src/main/java/com/example/rest_service/controller/CartController.java
package com.example.rest_service.controller;

import com.example.rest_service.dto.CartDTO;
import com.example.rest_service.dto.OrderDTO;
import com.example.rest_service.service.KeranjangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class KeranjangController {
    @Autowired private KeranjangService cartService;

    @GetMapping("/cart/orders")
    public List<OrderDTO> getCartOrders(@RequestHeader("Authorization") String token) {
        Long userId = validateToken(token);
        return cartService.getOrdersInCart(userId);
    }

    @GetMapping("/cart")
    public CartDTO getCart(@RequestHeader("Authorization") String token) {
        Long userId = validateToken(token);
        return cartService.getCartByUser(userId);
    }

    @PutMapping("/cart/orders/{orderId}")
    public OrderDTO updateOrder(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer orderId,
            @RequestParam Integer jumlahBarang
    ) {
        validateToken(token); // kalau validasi doang, gak usah pakai hasilnya
        return cartService.updateOrder(orderId, jumlahBarang);
    }

    @DeleteMapping("/cart/orders/{orderId}")
    public void deleteOrder(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer orderId
    ) {
        validateToken(token);
        cartService.removeOrder(orderId);
    }

    @PostMapping("/checkout")
    public CartDTO checkout(
            @RequestHeader("Authorization") String token,
            @RequestBody List<Integer> orderIds
    ) {
        Long userId = validateToken(token);
        return cartService.checkout(userId, orderIds);
    }

    private Long validateToken(String token) {
        // TODO: nanti lo ganti sama JWT decoder beneran
        return Long.parseLong(token);
    }
}
