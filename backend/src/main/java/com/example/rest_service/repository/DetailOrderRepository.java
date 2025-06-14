package com.example.rest_service.repository;

import com.example.rest_service.model.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, Integer> {
}
