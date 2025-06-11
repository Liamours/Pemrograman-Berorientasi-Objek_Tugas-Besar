package com.example.rest_service.repository;

import com.example.rest_service.model.Client;
import com.example.rest_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUser(User user);
}

