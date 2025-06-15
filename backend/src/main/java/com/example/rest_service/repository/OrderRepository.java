// src/main/java/com/example/rest_service/repository/OrderRepository.java
package com.example.rest_service.repository;

import com.example.rest_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByKeranjangKeranjangId(Integer keranjangId);
}
