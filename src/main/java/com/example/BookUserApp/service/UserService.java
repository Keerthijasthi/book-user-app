package com.example.BookUserApp.service;

import com.example.BookUserApp.model.User;
import com.example.BookUserApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepo.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setCreatedDate(updatedUser.getCreatedDate());
            return userRepo.save(user);
        });
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
