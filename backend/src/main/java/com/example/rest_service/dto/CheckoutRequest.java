package com.example.rest_service.dto;

import java.util.List;

public class CheckoutRequest {
    private List<Integer> orders;

    // Getter and Setter
    public List<Integer> getOrders() {
        return orders;
    }

    public void setOrders(List<Integer> orders) {
        this.orders = orders;
    }
}