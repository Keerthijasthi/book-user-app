package com.example.BookUserApp.exception;

import jakarta.persistence.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(Long id) {
        super("Book not found with id: " + id);
    }
}
