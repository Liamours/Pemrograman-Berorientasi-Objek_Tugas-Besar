package com.example.growandcheer.controller;

import com.example.growandcheer.model.User;
import com.example.growandcheer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        User created = userRepo.save(u);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User u) {
        if (!userRepo.existsById(id)) {
            return ResponseEntity.status(404).body("User not found");
        }
        u.setId(id);
        return ResponseEntity.ok(userRepo.save(u));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!userRepo.existsById(id)) {
            return ResponseEntity.status(404).body("User not found");
        }
        userRepo.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
