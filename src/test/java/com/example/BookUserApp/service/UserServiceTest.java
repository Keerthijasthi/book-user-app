package com.example.BookUserApp.service;

import com.example.BookUserApp.exception.UserNotFoundException;
import com.example.BookUserApp.model.User;
import com.example.BookUserApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User(1L, "John Doe", "john@example.com", LocalDate.now());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(testUser);
        when(userRepo.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        verify(userRepo).findAll();
    }

    @Test
    void testGetUserById_Found() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(testUser));

        User result = userService.getUserById(1L);
        assertEquals(testUser.getEmail(), result.getEmail());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(2L));
    }

    @Test
    void testGetUserByEmail_Found() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(testUser));

        User result = userService.getUserByEmail("john@example.com");
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testGetUserByEmail_NotFound() {
        when(userRepo.findByEmail("invalid@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail("invalid@example.com"));
    }

    @Test
    void testAddUser_Success() {
        testUser.setId(null);
        when(userRepo.save(testUser)).thenReturn(testUser);

        User result = userService.addUser(testUser);
        assertEquals("John Doe", result.getName());
        verify(userRepo).save(testUser);
    }

    @Test
    void testAddUser_AlreadyExists() {
        when(userRepo.existsById(1L)).thenReturn(true);
        testUser.setId(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                userService.addUser(testUser));

        assertEquals("User with ID 1 already exists", exception.getMessage());
    }

    @Test
    void testUpdateUser_Success() {
        User updated = new User(null, "Jane Doe", "jane@example.com", LocalDate.now());
        when(userRepo.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepo.save(any(User.class))).thenReturn(updated);

        User result = userService.updateUser(1L, updated);
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane@example.com", result.getEmail());
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, testUser));
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepo.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepo).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepo.existsById(2L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(2L));
    }
}
