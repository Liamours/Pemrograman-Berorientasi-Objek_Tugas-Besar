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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Tambahkan method ini untuk controller
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Get admin profile
    public Map<String, Object> getAdminProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("email", user.getEmail());
        profile.put("name", user.getName());
        profile.put("role", user.getPeran());
        profile.put("createdAt", user.getCreatedAt());
        profile.put("updatedAt", user.getUpdatedAt());

        return profile;
    }

    // Get client profile
    public Map<String, Object> getClientProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Client client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client details not found"));

        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("email", user.getEmail());
        profile.put("name", user.getName());
        profile.put("role", user.getPeran());
        profile.put("isMember", client.isIsmember());
        profile.put("address", client.getAlamat());
        profile.put("createdAt", user.getCreatedAt());
        profile.put("updatedAt", user.getUpdatedAt());

        return profile;
    }

    // Update profile
    @Transactional
    public void updateProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setName(request.getName());
        userRepository.save(user);

        if (user.getPeran() == User.Role.Client) {
            Client client = clientRepository.findById(user.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client data not found"));
            client.setAlamat(request.getAddress());
            clientRepository.save(client);
        }
    }

    // Change password
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
        userRepository.save(user);
    }

    // Delete account
    @Transactional
    public void deleteAccount(String email, DeleteAccountRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if(!user.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Current password is incorrect");
        }

        if (user.getPeran() == User.Role.Client) {
            clientRepository.deleteById(user.getId());
            orderRepository.deleteById(user.getId().intValue());
        }

        userRepository.delete(user);
    }

    // Upgrade to member
    @Transactional
    public void upgradeToMember(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Client client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client data not found"));

        if (client.isIsmember()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already a member");
        }

        client.setIsmember(true);
        clientRepository.save(client);
    }

    // Get all users
    public List<Map<String, Object>> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("id", user.getId());
                    userData.put("email", user.getEmail());
                    userData.put("name", user.getName());
                    userData.put("role", user.getPeran());
                    userData.put("createdAt", user.getCreatedAt());
                    userData.put("updatedAt", user.getUpdatedAt());

                    if (user.getPeran() == User.Role.Client) {
                        clientRepository.findById(user.getId()).ifPresent(client -> {
                            userData.put("isMember", client.isIsmember());
                            userData.put("address", client.getAlamat());
                        });
                    }

                    return userData;
                })
                .collect(Collectors.toList());
    }

    // Change user role
    @Transactional
    public void changeUserRole(String adminEmail, ChangeRoleRequest request) {
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        User userToUpdate = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (userToUpdate.getEmail().equals(adminEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot change your own role");
        }

        User.Role newRole = (userToUpdate.getPeran() == User.Role.Admin) ? User.Role.Client : User.Role.Admin;

        if (userToUpdate.getPeran() == User.Role.Client && newRole == User.Role.Admin) {
            clientRepository.findById(userToUpdate.getId()).ifPresent(clientRepository::delete);
        } else if (userToUpdate.getPeran() == User.Role.Admin && newRole == User.Role.Client) {
            if (!clientRepository.existsById(userToUpdate.getId())) {
                Client newClient = new Client();
                newClient.setUser(userToUpdate);
                newClient.setIsmember(false);
                clientRepository.save(newClient);
            }
        }

        userToUpdate.setPeran(newRole);
        userRepository.save(userToUpdate);
    }
}