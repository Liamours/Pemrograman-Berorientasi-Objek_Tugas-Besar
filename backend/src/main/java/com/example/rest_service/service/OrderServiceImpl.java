package com.example.rest_service.service;

import com.example.rest_service.dto.*;
import com.example.rest_service.model.*;
import com.example.rest_service.model.Order.StatusOrder;
import com.example.rest_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KeranjangRepository keranjangRepository;

    @Autowired
    private BarangRepository barangRepository;

    // GET /keranjang?user_id=
    @Override
    public ListOrderResponse getKeranjangByUserId(int userId) {
        User user = userRepository.findById((long) userId).orElseThrow();
        Keranjang keranjang = keranjangRepository.findByUser(user).orElseThrow();
        Client client = clientRepository.findByUser(user).orElseThrow();

        List<Order> orderList = orderRepository.findByKeranjangAndStatusOrder(keranjang, StatusOrder.Pending_Client);
        List<OrderData> data = new ArrayList<>();

        for (Order order : orderList) {
            OrderData dto = new OrderData();
            dto.setOrderId(order.getOrderId());
            dto.setJumlahBarang(order.getJumlahBarang());
            dto.setStatusOrder(order.getStatusOrder().name());
            dto.setNamaBarang(order.getBarang().getNamaBarang());
            dto.setStock(order.getBarang().getStokBarang());
            data.add(dto);
        }

        ListOrderResponse response = new ListOrderResponse();
        response.setStatus(true);
        response.setMessage("Berhasil ambil keranjang");
        response.setMember(client.isIsmember());
        response.setData(data);
        return response;
    }

    // POST /keranjang/checkout
    @Override
    public CheckoutResponse checkout(CheckoutRequest request, int userId) {
        User user = userRepository.findById((long) userId).orElseThrow();
        List<CheckoutResponse.CheckoutItemResult> resultList = new ArrayList<>();
        double totalHarga = 0;
        boolean semuaSukses = true;

        for (CheckoutRequest.OrderIdWrapper item : request.getData()) {
            int orderId = item.getOrder_id();
            Optional<Order> optionalOrder = orderRepository.findByOrderIdAndUser(orderId, user);

            if (optionalOrder.isEmpty()) continue;
            Order order = optionalOrder.get();
            CheckoutResponse.CheckoutItemResult result = new CheckoutResponse.CheckoutItemResult();
            result.setNama_barang(order.getBarang().getNamaBarang());
            result.setJumlah_barang(order.getJumlahBarang());

            Barang barang = order.getBarang();
            if (barang.getStokBarang() >= order.getJumlahBarang()) {
                barang.setStokBarang(barang.getStokBarang() - order.getJumlahBarang());
                order.setStatusOrder(StatusOrder.Pending_Admin);
                barangRepository.save(barang);
                orderRepository.save(order);
                result.setMessage("Sukses");
            } else {
                result.setMessage("Stok tidak cukup");
                semuaSukses = false;
            }

            resultList.add(result);
        }

        CheckoutResponse response = new CheckoutResponse();
        response.setStatus(semuaSukses);
        response.setTotalHarga(totalHarga);
        response.setData(resultList);
        return response;
    }

    // DELETE /keranjang/order
    @Override
    public void deleteOrder(int orderId, int userId) {
        User user = userRepository.findById((long) userId).orElseThrow();
        Order order = orderRepository.findByOrderIdAndUser(orderId, user).orElseThrow();
        orderRepository.delete(order);
    }

    @Override
    public void updateOrderJumlah(int orderId, int userId, int jumlah) {
        User user = userRepository.findById((long) userId).orElseThrow();
        Order order = orderRepository.findByOrderIdAndUser(orderId, user).orElseThrow();

        if (order.getStatusOrder() != StatusOrder.Pending_Client) {
            throw new IllegalStateException("Order sudah diproses, tidak bisa diubah");
        }

        // Update jumlah dan hitung ulang total harga
        order.setJumlahBarang(jumlah);
        orderRepository.save(order);
    }
}
