package com.example.BookUserApp.service;

import com.example.BookUserApp.exception.UserNotFoundException;
import com.example.BookUserApp.model.User;
import com.example.BookUserApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(-1L)); // or customize exception for email search
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepo.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setCreatedDate(updatedUser.getCreatedDate());
            return userRepo.save(user);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepo.deleteById(id);
    }
}
