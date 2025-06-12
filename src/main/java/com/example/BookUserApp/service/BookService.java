package com.example.BookUserApp.service;

import com.example.BookUserApp.exception.BookNotFoundException;
import com.example.BookUserApp.model.Book;
import com.example.BookUserApp.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<Book> books = bookRepo.findByAuthor(author);
        if (books.isEmpty()) {
            throw new IllegalArgumentException("No books found for author: " + author + ". Please enter a valid/full name.");
        }
        return books;
    }


    public Book addBook(Book book) {
        if (book.getId() != null && bookRepo.existsById(book.getId())) {
            throw new IllegalArgumentException("Book with ID " + book.getId() + " already exists");
        }
        return bookRepo.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        return bookRepo.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setIsbn(updatedBook.getIsbn());
            book.setPublishedDate(updatedBook.getPublishedDate());
            return bookRepo.save(book);
        }).orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteBook(Long id) {
        if (!bookRepo.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepo.deleteById(id);
    }
}
