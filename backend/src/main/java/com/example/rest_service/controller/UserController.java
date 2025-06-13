package com.example.rest_service.controller;

import java.util.*;

import com.example.rest_service.dto.ApiResponse;
import com.example.rest_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.rest_service.repository.UserRepository;
import com.example.rest_service.repository.ClientRepository;
import com.example.rest_service.security.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import com.example.rest_service.model.User;
import com.example.rest_service.model.Client;
import com.example.rest_service.dto.ChangePasswordRequest;
import com.example.rest_service.dto.UpdateProfileRequest;
import com.example.rest_service.dto.ChangeRoleRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    public UserController(UserRepository userRepository, ClientRepository clientRepository,OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/profile/admin")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getAdminProfile(Authentication authentication) {
        String email = authentication.getName();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();

        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("email", user.getEmail());
        profile.put("name", user.getName());
        profile.put("role", user.getPeran());
        profile.put("createdAt", user.getCreatedAt());
        profile.put("updatedAt", user.getUpdatedAt());

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profile/client")
    @PreAuthorize("hasRole('Client')")
    public ResponseEntity<?> getClientProfile(Authentication authentication) {
        String email = authentication.getName();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();
        Optional<Client> clientOptional = clientRepository.findById(user.getId());
        if (clientOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client details not found");
        }

        Client client = clientOptional.get();

        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("email", user.getEmail());
        profile.put("name", user.getName());
        profile.put("role", user.getPeran());
        profile.put("isMember", client.isIsmember());
        profile.put("address", client.getAlamat());
        profile.put("createdAt", user.getCreatedAt());
        profile.put("updatedAt", user.getUpdatedAt());

        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request,
            BindingResult bindingResult) {

        // Validasi input
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Update data user
        user.setName(request.getName());
        userRepository.save(user);

        // Jika client, update alamat
        if (user.getPeran() == User.Role.Client) {
            Client client = clientRepository.findById(user.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client data not found"));
            client.setAlamat(request.getAddress());
            clientRepository.save(client);
        }

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "Berhasil Mengubah"));
    }

    @PutMapping("/password/change")
    public ResponseEntity<?> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request,
            BindingResult bindingResult) {

        // Validasi input
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        // Validasi konfirmasi password
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "New password and confirmation password don't match"));
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Verifikasi password lama (tanpa encryption)
        if (!request.getCurrentPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Password sekarang salah"));
        }

        // Update password baru (tanpa encryption)
        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.ok() 
                .body(new ApiResponse(true, "Berhasil Mengubah"));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(
            Authentication authentication) { // Require password confirmation

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        try {
            // Delete client record first if exists
            if (user.getPeran() == User.Role.Client) {
                clientRepository.deleteById(user.getId());
                orderRepository.deleteById(user.getId().intValue());
            }

            // Then delete user
            userRepository.delete(user);

            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "Account deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete account"));
        }
    }
    @PutMapping("/member")
    @PreAuthorize("hasRole('Client')")
    public ResponseEntity<?> upgradeToMember(Authentication authentication) {
        String email = authentication.getName();

        // Find the user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        // Find the client record
        Client client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client data not found"));

        // Check if already a member
        if (client.isIsmember()) {
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "User sudah menjadi member"));
        }

        // Upgrade to member
        client.setIsmember(true);
        clientRepository.save(client);

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "User berhasil diupgrade menjadi member"));
    }

    @GetMapping("/alluser")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getAllUsers() {
        // Get all users from repository
        List<User> users = userRepository.findAll();

        // Prepare response list
        List<Map<String, Object>> response = new ArrayList<>();

        for (User user : users) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("email", user.getEmail());
            userData.put("name", user.getName());
            userData.put("role", user.getPeran());
            userData.put("createdAt", user.getCreatedAt());
            userData.put("updatedAt", user.getUpdatedAt());

            // If the user is a client, add client-specific data
            if (user.getPeran() == User.Role.Client) {
                Optional<Client> clientOptional = clientRepository.findById(user.getId());
                clientOptional.ifPresent(client -> {
                    userData.put("isMember", client.isIsmember());
                    userData.put("address", client.getAlamat());
                });
            }

            response.add(userData);
        }

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "Successfully retrieved all users", response));
    }

    @Transactional
    @PutMapping("/changerole")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> changeUserRole(
            @Valid @RequestBody ChangeRoleRequest request,
            BindingResult bindingResult,
            Authentication authentication) {
        System.out.println("Authenticated email: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());

        // Validasi input
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        // Dapatkan admin yang sedang login
        String adminEmail = authentication.getName();
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        // Cari user yang akan diubah
        User userToUpdate = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Validasi: Admin tidak bisa mengubah role sendiri
        if (userToUpdate.getEmail().equals(adminEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, "Tidak dapat mengubah role sendiri"));
        }

        // Tentukan perubahan role
        User.Role currentRole = userToUpdate.getPeran();
        User.Role newRole;
        if (currentRole == User.Role.Admin) {
            newRole = User.Role.Client;
        } else {
            newRole = User.Role.Admin;
        }

        // Handle perubahan role
        if (currentRole == User.Role.Client && newRole == User.Role.Admin) {
            // Hapus data client jika ada
            clientRepository.findById(userToUpdate.getId()).ifPresent(client -> {
                clientRepository.delete(client);
            });
        } else if (currentRole == User.Role.Admin && newRole == User.Role.Client) {
            // Buat data client baru jika tidak ada
            if (!clientRepository.existsById(userToUpdate.getId())) {
                Client newClient = new Client();
                newClient.setUser(userToUpdate);
                newClient.setIsmember(false);
                clientRepository.save(newClient);
            }
        }

        // Update role user
        userToUpdate.setPeran(newRole);
        userRepository.save(userToUpdate);

        String roleName;
        if (newRole == User.Role.Admin) {
            roleName = "Admin";
        } else {
            roleName = "Client";
        }
        return ResponseEntity.ok()
                .body(new ApiResponse(true, "Role berhasil diubah menjadi " + roleName));
    }
}