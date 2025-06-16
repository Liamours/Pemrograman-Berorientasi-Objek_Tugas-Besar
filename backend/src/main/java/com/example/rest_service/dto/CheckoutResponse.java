package com.example.rest_service.dto;


import lombok.Data;
import java.util.List;

@Data
public class CheckoutResponse {
    private String message;
    private List<OrderDTO> checkedOutOrders;

    public CheckoutResponse(String message, List<OrderDTO> checkedOutOrders) {
        this.message = message;
        this.checkedOutOrders = checkedOutOrders;
    }
}