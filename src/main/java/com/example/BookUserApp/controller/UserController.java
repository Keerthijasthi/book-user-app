package com.example.BookUserApp.controller;

import com.example.BookUserApp.model.User;
import com.example.BookUserApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/email")
    public ResponseEntity<User> getUserByEmail(@Valid @RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be blank");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Email format is invalid");
        }

        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }


    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        Map<String, String> response = Map.of("message", "User deleted successfully");
        return ResponseEntity.ok(response);
    }

}
