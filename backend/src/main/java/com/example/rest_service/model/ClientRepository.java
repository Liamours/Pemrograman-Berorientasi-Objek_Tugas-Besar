package com.example.rest_service.repository;

import com.example.rest_service.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    @Query("SELECT c FROM Client c WHERE c.clientDetail.ismember = true")
    List<Client> findAllMembers();

    @Query("SELECT c FROM Client c WHERE c.clientDetail.ismember = false")
    List<Client> findAllNonMembers();

    @Query("SELECT c FROM Client c WHERE c.clientDetail.alamat LIKE %:alamat%")
    List<Client> findByAlamatContaining(@Param("alamat") String alamat);
}