package com.example.rest_service.service;

import com.example.rest_service.dto.*;
import com.example.rest_service.model.*;
import com.example.rest_service.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ClientDetailRepository clientDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KeranjangRepository keranjangRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public Map<String, Object> getAdminProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!(user instanceof Admin)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied - not an admin");
        }

        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("email", user.getEmail());
        profile.put("name", user.getNamaUser());
        profile.put("role", user.getPeran());
        profile.put("createdAt", user.getCreatedAt());
        profile.put("updatedAt", user.getUpdatedAt());

        return profile;
    }

    public Map<String, Object> getClientProfile(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        Map<String, Object> profile = new HashMap<>();
        profile.put("id", client.getId());
        profile.put("email", client.getEmail());
        profile.put("name", client.getNamaUser());
        profile.put("role", client.getPeran());
        profile.put("isMember", client.isMember());
        profile.put("address", client.getAlamat());
        profile.put("createdAt", client.getCreatedAt());
        profile.put("updatedAt", client.getUpdatedAt());

        return profile;
    }

    

    @Transactional
    public void changePassword(String email, ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password and confirmation password don't match");
        }

        if (!request.getCurrentPassword().equals(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Current password is incorrect");
        }

        user.setPassword(request.getNewPassword());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional
    public void deleteAccount(String email, DeleteAccountRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Current password is incorrect");
        }

        if (user instanceof Client client) {
            keranjangRepository.deleteAll(client.getKeranjangs());
            orderRepository.deleteAll(client.getOrders());

            if (client.getClientDetails() != null) {
                clientDetailRepository.delete(client.getClientDetails());
            }
        }

        userRepository.delete(user);
    }

    @Transactional
    public void upgradeToMember(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        if (client.isMember()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already a member");
        }

        client.setMember(true);
        client.setUpdatedAt(LocalDateTime.now());
        clientRepository.save(client);
    }

}