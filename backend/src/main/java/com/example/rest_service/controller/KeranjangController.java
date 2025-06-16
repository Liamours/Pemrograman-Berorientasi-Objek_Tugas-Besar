package com.example.rest_service.controller;

import com.example.rest_service.dto.CartDTO;
import com.example.rest_service.dto.CheckoutResponse;
import com.example.rest_service.dto.OrderDTO;
import com.example.rest_service.dto.UpdateOrderRequest;
import com.example.rest_service.service.KeranjangService;
import com.example.rest_service.service.UserService;
import com.example.rest_service.security.JwtTokenUtil;
import com.example.rest_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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
            @RequestBody UpdateOrderRequest request
    ) {
        validateToken(token);
        return cartService.updateOrder(orderId, request.getJumlahBarang());
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
    public ResponseEntity<CheckoutResponse> checkout(
            @RequestHeader("Authorization") String token,
            @RequestBody List<Integer> orderIds
    ) {
        Long userId = validateToken(token);
        List<OrderDTO> processedOrders = cartService.checkout(userId, orderIds);

        return ResponseEntity.ok(
                new CheckoutResponse("Checkout berhasil", processedOrders)
        );
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

            return user.getId();

        } catch (Exception e) {
            throw new RuntimeException("Invalid token: " + e.getMessage());
        }
    }
}