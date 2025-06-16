package com.example.rest_service.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {
    private List<OrderDTO> orders;

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

}