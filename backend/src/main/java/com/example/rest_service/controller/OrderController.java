// src/main/java/com/example/rest_service/controller/OrderController.java
package com.example.rest_service.controller;

import com.example.rest_service.dto.OrderDTO;
import com.example.rest_service.model.User;
import com.example.rest_service.repository.UserRepository;
import com.example.rest_service.service.OrderService;
import com.example.rest_service.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/add")
    public OrderDTO addOrder(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody OrderRequest request
    ) {
        Long userId = validateTokenAndGetUserId(authHeader);
        System.out.println("USNNNNNNNNNNNNNNNNN " +userId);
        return orderService.addOrder(
                userId,
                request.getBarangId(),
                request.getJumlahBarang(),
                request.getAlamatTujuan()
        );
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrderById(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId);
    }

    // Tambahan: POST method untuk get order by ID via request body
    @PostMapping("/get")
    public OrderDTO getOrderByIdFromBody(@RequestBody GetOrderRequest request) {
        return orderService.getOrderById(request.getOrderId());
    }

    private Long validateTokenAndGetUserId(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix

            try {
                // Coba dapatkan username dari token
                String username = jwtTokenUtil.extractUsername(token);
                System.out.println("USNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN"+username);
                if (username != null) {
                    // Cari user ID dari database berdasarkan username
                    User user = userRepository.findByEmail(username)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    return user.getId();
                }

                throw new RuntimeException("Username not found in token");
            } catch (Exception e) {
                throw new RuntimeException("Invalid JWT token: " + e.getMessage());
            }
        }

        throw new RuntimeException("Authorization header must start with Bearer");
    }

    public static class OrderRequest {
        private Integer barangId;
        private Integer jumlahBarang;
        private String alamatTujuan;

        public OrderRequest() {}

        public Integer getBarangId() {
            return barangId;
        }

        public void setBarangId(Integer barangId) {
            this.barangId = barangId;
        }

        public Integer getJumlahBarang() {
            return jumlahBarang;
        }

        public void setJumlahBarang(Integer jumlahBarang) {
            this.jumlahBarang = jumlahBarang;
        }

        public String getAlamatTujuan() {
            return alamatTujuan;
        }

        public void setAlamatTujuan(String alamatTujuan) {
            this.alamatTujuan = alamatTujuan;
        }
    }

    public static class GetOrderRequest {
        private Integer orderId;

        public GetOrderRequest() {}

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }
    }
}