// src/main/java/com/example/rest_service/repository/OrderRepository.java
package com.example.rest_service.repository;

import com.example.rest_service.model.Order;
import com.example.rest_service.model.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByKeranjangKeranjangId(Integer keranjangId);

    @Modifying
    @Query(value = "DELETE FROM `order` WHERE order_id = :orderId", nativeQuery = true)
    void executeDeleteOrder(@Param("orderId") Integer orderId);
    List<Order> findByStatusOrder(StatusOrder statusOrder);
}
