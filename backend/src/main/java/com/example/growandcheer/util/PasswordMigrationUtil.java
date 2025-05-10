package com.example.growandcheer.util;

import com.example.growandcheer.model.User;
import com.example.growandcheer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.InitializingBean;
import java.util.List;

@Component
public class PasswordMigrationUtil implements InitializingBean {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method will run once after the application starts.
     * It will hash all plain text passwords in the database and update them.
     * After running once, you should remove or comment out this class to avoid re-hashing.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            String password = user.getPassword();
            // Check if password is already hashed (BCrypt hashes start with $2a$, $2b$, or $2y$)
            if (password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$") && !password.startsWith("$2y$")) {
                String hashed = passwordEncoder.encode(password);
                user.setPassword(hashed);
                userRepository.save(user);
                System.out.println("Password hashed for user: " + user.getEmail());
            } else {
                System.out.println("Password already hashed for user: " + user.getEmail());
            }
        }
        System.out.println("Password migration completed.");
    }
}
