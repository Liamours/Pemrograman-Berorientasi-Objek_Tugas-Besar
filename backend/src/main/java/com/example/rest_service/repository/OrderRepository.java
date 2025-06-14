package com.example.rest_service.repository;

import com.example.rest_service.model.Keranjang;
import com.example.rest_service.model.Order;
import com.example.rest_service.model.Order.StatusOrder;
import com.example.rest_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // 1. Ambil semua order di keranjang tertentu (misal untuk GET /cart)
    List<Order> findByKeranjangAndStatusOrder(Keranjang keranjang, StatusOrder statusOrder);

    // 2. Ambil semua order milik user dengan status tertentu
    List<Order> findByUserAndStatusOrder(User user, StatusOrder statusOrder);

    // 3. (Opsional) Cari satu order by id dan user, untuk memastikan user punya order itu
    Optional<Order> findByOrderIdAndUser(Integer orderId, User user);

    // 4. (Opsional) Hapus semua order inCart di keranjang (kalau mau clear cart)
    void deleteByKeranjangAndStatusOrder(Keranjang keranjang, StatusOrder statusOrder);

}