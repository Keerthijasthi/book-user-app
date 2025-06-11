package com.example.BookUserApp.model;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "BookUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private LocalDate createdDate;

    public User() {}

    public User(String name, String email, LocalDate createdDate) {
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
