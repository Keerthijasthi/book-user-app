package com.example.BookUserApp.controller;

import com.example.BookUserApp.model.Book;
import com.example.BookUserApp.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll(); // Clean before each test

        Book book = new Book(null, "Test Book", "Test Author", "1111111111", LocalDate.of(2022, 1, 1));
        bookRepository.save(book);
    }

    @Test
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }

    @Test
    void testGetBooksByAuthor() throws Exception {
        mockMvc.perform(get("/api/books/author/Test Author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value("Test Author"));
    }

    @Test
    void testAddBook_Success() throws Exception {
        Book newBook = new Book(null, "New Title", "New Author", "2222222222", LocalDate.of(2023, 1, 1));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"));
    }

    @Test
    void testAddBook_ValidationFail() throws Exception {
        Book invalidBook = new Book(null, "", "", "3333333333", LocalDate.of(2023, 1, 1));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBook)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title must not be blank"))
                .andExpect(jsonPath("$.author").value("Author must not be blank"));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book book = bookRepository.findAll().get(0);
        book.setTitle("Updated Title");

        mockMvc.perform(put("/api/books/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void testDeleteBook() throws Exception {
        Book book = bookRepository.findAll().get(0);

        mockMvc.perform(delete("/api/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book deleted successfully"));
    }

    @Test
    void testGetBooksByAuthor_InvalidInput() throws Exception {
        mockMvc.perform(get("/api/books/author/123@Invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Author name must contain only alphabetic characters"));
    }
}
