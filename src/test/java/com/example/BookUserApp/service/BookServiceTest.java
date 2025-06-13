package com.example.BookUserApp.service;

import com.example.BookUserApp.exception.BookNotFoundException;
import com.example.BookUserApp.model.Book;
import com.example.BookUserApp.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepo;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Test Title", "Test Author", "123456", LocalDate.now());
    }

    @Test
    void testGetAllBooks() {
        when(bookRepo.findAll()).thenReturn(List.of(book));
        assertEquals(1, bookService.getAllBooks().size());
    }
    @Test
    void testGetAllBooks_NoBooksFound() {
        when(bookRepo.findAll()).thenReturn(Collections.emptyList());

        List<Book> books = bookService.getAllBooks();

        assertNotNull(books);
        assertTrue(books.isEmpty());
    }


    @Test
    void testGetBooksByAuthorFound() {
        when(bookRepo.findByAuthor("Test Author")).thenReturn(List.of(book));
        List<Book> books = bookService.getBooksByAuthor("Test Author");
        assertEquals(1, books.size());
        assertEquals("Test Author", books.get(0).getAuthor());
    }

    @Test
    void testGetBooksByAuthorNotFound() {
        when(bookRepo.findByAuthor("Unknown")).thenReturn(Collections.emptyList());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> bookService.getBooksByAuthor("Unknown"));
        assertTrue(ex.getMessage().contains("No books found for author"));
    }

    @Test
    void testGetBooksByAuthorBlankAuthor() {
        assertThrows(IllegalArgumentException.class, () -> bookService.getBooksByAuthor(""));
        assertThrows(IllegalArgumentException.class, () -> bookService.getBooksByAuthor("   "));
        assertThrows(IllegalArgumentException.class, () -> bookService.getBooksByAuthor(null));
    }

    @Test
    void testGetBooksByAuthorInvalidCharacters() {
        assertThrows(IllegalArgumentException.class, () -> bookService.getBooksByAuthor("Author123"));
        assertThrows(IllegalArgumentException.class, () -> bookService.getBooksByAuthor("John_Doe!"));
    }

    @Test
    void testAddBookSuccess() {
        Book newBook = new Book(1L, "New Title", "New Author", "1234567890", null);
        when(bookRepo.save(newBook)).thenReturn(newBook);

        Book savedBook = bookService.addBook(newBook);
        assertEquals("New Title", savedBook.getTitle());
    }

    @Test
    void testAddBookWithExistingIdThrows() {
        Book existingBook = new Book(1L, "Existing", "Author", "123", null);
        when(bookRepo.existsById(1L)).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> bookService.addBook(existingBook));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void testUpdateBookSuccess() {
        Long id = 1L;
        Book existingBook = new Book(id, "Old Title", "Old Author", "111", null);
        Book updatedBook = new Book(null, "New Title", "New Author", "222", null);

        when(bookRepo.findById(id)).thenReturn(Optional.of(existingBook));
        when(bookRepo.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.updateBook(id, updatedBook);
        assertEquals("New Title", result.getTitle());
        assertEquals("New Author", result.getAuthor());
    }

    @Test
    void testUpdateBookNotFoundThrows() {
        Long id = 2L;
        Book updatedBook = new Book(null, "New Title", "New Author", "222", null);

        when(bookRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(id, updatedBook));
    }

    @Test
    void testDeleteBookSuccess() {
        Long id = 1L;
        when(bookRepo.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> bookService.deleteBook(id));
        verify(bookRepo).deleteById(id);
    }

    @Test
    void testDeleteBookNotFoundThrows() {
        Long id = 2L;
        when(bookRepo.existsById(id)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(id));
    }
}
