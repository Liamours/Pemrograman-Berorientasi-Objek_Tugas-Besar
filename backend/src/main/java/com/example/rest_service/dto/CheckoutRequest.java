package com.example.rest_service.dto;

import java.util.List;

public class CheckoutRequest {
    private List<OrderIdWrapper> data;

    public List<OrderIdWrapper> getData() {
        return data;
    }

    public void setData(List<OrderIdWrapper> data) {
        this.data = data;
    }

    public static class OrderIdWrapper {
        private Integer order_id;

        public Integer getOrder_id() { return order_id; }
        public void setOrder_id(Integer order_id) { this.order_id = order_id; }
    }
}