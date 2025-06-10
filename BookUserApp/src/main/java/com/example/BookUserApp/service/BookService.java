package com.example.BookUserApp.service;

import com.example.BookUserApp.model.Book;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {
//
//    public List<Book> getAllBooks() {
//        return Arrays.asList(
//                new Book((long)1, "Head First Java", "Kathy Sierra", "9780132350884", LocalDate.of(2008, 8, 1)),
//                new Book((long)2, "Effective Java", "Joshua Bloch", "9780134685991", LocalDate.of(2018, 1, 6))
//        );
//
//    }
//
//    public Book getBookById(Long id) {
//        return new Book(id, "Mock Book", "Mock Author", "0000000000", LocalDate.now());
//    }

    private final List<Book> books = Arrays.asList(
            new Book((long)1, "Head First Java", "Kathy Sierra", "9780132350884", LocalDate.of(2008, 8, 1)),
            new Book((long)2, "Effective Java", "Joshua Bloch", "9780134685991", LocalDate.of(2018, 1, 6))
    );

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addBook(Book book) {

    }

    public void updateBook(Book book) {

    }

    public void deleteBook(Long id) {

    }
}
