package com.example.rest_service.repository;

import com.example.rest_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.peran = :role")
    List<User> findByRole(@Param("role") User.Role role);

    @Query("SELECT u FROM User u WHERE u.peran = 'Client'")
    List<User> findAllClients();

    @Query("SELECT u FROM User u WHERE u.peran = 'Admin'")
    List<User> findAllAdmins();


}