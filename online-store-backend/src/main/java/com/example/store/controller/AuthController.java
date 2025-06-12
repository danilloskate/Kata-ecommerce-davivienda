package com.example.store.controller;

import com.example.store.entity.User;
import com.example.store.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData) {
        String email = userData.get("email");
        String password = userData.get("password");
        String role = userData.get("role");

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // Nota: en producción deberías encriptar esto
        user.setRole(role != null ? role : "CUSTOMER");

        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // Simular token (en producción usar JWT)
        return ResponseEntity.ok(Map.of("token", "fake-jwt-token", "role", userOpt.get().getRole()));
    }
}
