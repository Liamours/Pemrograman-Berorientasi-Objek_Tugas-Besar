package com.example.rest_service.dto;

import java.util.List;

public class CartDTO {
    private List<OrderDTO> orders;
    private double total;  // Tambahkan field total

    // Getter & Setter
    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {  // Method setTotal yang diperlukan
        this.total = total;
    }
}