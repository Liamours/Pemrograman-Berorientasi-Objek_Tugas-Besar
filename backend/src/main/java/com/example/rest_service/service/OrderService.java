package com.example.rest_service.service;

import com.example.rest_service.dto.CheckoutRequest;
import com.example.rest_service.dto.CheckoutResponse;
import com.example.rest_service.dto.ListOrderResponse;


public interface OrderService {

    ListOrderResponse getKeranjangByUserId(int userId);

    CheckoutResponse checkout(CheckoutRequest request, int userId);

    void deleteOrder(int orderId, int userId);


}
