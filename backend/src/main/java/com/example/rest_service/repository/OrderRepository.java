package com.example.rest_service.repository;

import com.example.rest_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Custom queries for Order can be added here
}
