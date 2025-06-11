package com.example.BookUserApp.service;

import com.example.BookUserApp.model.Book;
import com.example.BookUserApp.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepo.findByAuthor(author);
    }

    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        return bookRepo.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setIsbn(updatedBook.getIsbn());
            book.setPublishedDate(updatedBook.getPublishedDate());
            return bookRepo.save(book);
        });
    }

    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }
}
