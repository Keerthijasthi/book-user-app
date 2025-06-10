package com.example.BookUserApp.service;

import com.example.BookUserApp.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

//    public List<User> getAllUsers() {
//        return Arrays.asList(
//                new User((long)1, "Alice", "alice@example.com", LocalDate.of(2023, 1, 1)),
//                new User((long)2, "Bob", "bob@example.com", LocalDate.of(2023, 5, 12))
//        );
//    }
//
//    public User getUserById(Long id) {
//        return new User(id, "Mock User", "mockuser@example.com", LocalDate.now());
//    }
private final List<User> users = Arrays.asList(
        new User((long)1, "Alice Johnson", "alice.johnson@example.com", LocalDate.of(2020, 1, 15)),
        new User((long)2, "Bob Smith", "bob.smith@example.com", LocalDate.of(2021, 5, 22))
);

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addUser(User user) {

    }

    public void updateUser(User user) {

    }

    public void deleteUser(Long id) {

    }
}
