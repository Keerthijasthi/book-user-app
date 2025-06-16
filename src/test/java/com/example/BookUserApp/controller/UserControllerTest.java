package com.example.BookUserApp.controller;

import com.example.BookUserApp.model.User;
import com.example.BookUserApp.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void testAddUser_Success() throws Exception {
        User user = new User(null, "Alice", "alice@example.com", LocalDate.now());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));

        assertThat(userRepository.findAll()).hasSize(1);
    }

    @Test
    void testGetUserById_Success() throws Exception {
        User saved = userRepository.save(new User(null, "Bob", "bob@example.com", LocalDate.now()));

        mockMvc.perform(get("/api/users/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bob"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        userRepository.save(new User(null, "John", "john@example.com", LocalDate.now()));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        User user = userRepository.save(new User(null, "Old Name", "old@example.com", LocalDate.now()));
        user.setName("New Name");
        user.setEmail("new@example.com");

        mockMvc.perform(put("/api/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.email").value("new@example.com"));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        User user = userRepository.save(new User(null, "To Delete", "delete@example.com", LocalDate.now()));

        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isNoContent());

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void testGetUserByEmail_Success() throws Exception {
        userRepository.save(new User(null, "EmailUser", "emailuser@example.com", LocalDate.now()));

        mockMvc.perform(post("/api/users/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email", "emailuser@example.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("EmailUser"));
    }

    @Test
    void testGetUserByEmail_Invalid() throws Exception {
        mockMvc.perform(post("/api/users/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email", "invalid-email"))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email format is invalid"));
    }

    @Test
    void testAddUser_InvalidFields() throws Exception {
        User user = new User(null, "", "bad-email", LocalDate.now());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name must not be blank"))
                .andExpect(jsonPath("$.email").value("Email should be valid"));
    }
}
