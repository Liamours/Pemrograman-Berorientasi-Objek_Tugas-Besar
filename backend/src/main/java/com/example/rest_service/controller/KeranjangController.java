package com.example.rest_service.controller;

import com.example.rest_service.dto.CartDTO;
import com.example.rest_service.dto.OrderDTO;
import com.example.rest_service.service.KeranjangService;
import com.example.rest_service.service.UserService;
import com.example.rest_service.security.JwtTokenUtil;
import com.example.rest_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class KeranjangController {

    @Autowired
    private KeranjangService cartService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

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
        validateToken(token);
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

    private Long validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid authorization header");
        }

        String token = authHeader.substring(7);

        try {
            String username = jwtTokenUtil.extractUsername(token);

            User user = userService.findByEmail(username);
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // Gunakan getId() bukan getUserId()
            return user.getId();

        } catch (Exception e) {
            throw new RuntimeException("Invalid token: " + e.getMessage());
        }
    }
}