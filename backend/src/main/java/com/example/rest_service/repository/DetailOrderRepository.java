package com.example.rest_service.repository;

import com.example.rest_service.model.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, Integer> {
}
