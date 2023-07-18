package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.Book;
import com.rburaksaritas.booklendingapi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    // Test 1: Get Book by ISBN
    @Test
    public void BookService_Get_ReturnsBook() {
        String isbn = "978-3-16-148410-0";
        Book book = new Book();
        book.setIsbn(isbn);
        when(bookRepository.findById(isbn)).thenReturn(Optional.of(book));
        Book fetchedBook = bookService.getBook(isbn);
        assertEquals(isbn, fetchedBook.getIsbn());
    }

    // Test 2: Save Book
    @Test
    public void BookService_Save_ReturnsSavedBook() {
        Book book = new Book();
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book savedBook = bookService.saveBook(book);
        assertNotNull(savedBook);
    }

    // Test 3: Delete Book
    @Test
    public void BookService_Delete_DeletesBook() {
        String isbn = "978-3-16-148410-0";
        Book book = new Book();
        book.setIsbn(isbn);
        when(bookRepository.findById(isbn)).thenReturn(Optional.of(book));
        boolean isDeleted = bookService.deleteBook(isbn);
        assertTrue(isDeleted);
        verify(bookRepository, times(1)).delete(book);
    }

    // Test 4: Update Book
    @Test
    public void BookService_Update_ReturnsUpdatedBook() {
        String isbn = "978-3-16-148410-0";
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        when(bookRepository.findById(isbn)).thenReturn(Optional.of(book));
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Test Book");
        updatedBook.setAuthor("Updated Test Author");
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        Book newBook = bookService.updateBook(isbn, updatedBook);
        assertEquals("Updated Test Book", newBook.getTitle());
        assertEquals("Updated Test Author", newBook.getAuthor());
    }

    // Test 5: Try to get non-existing Book
    @Test
    public void BookService_Get_NonExistingBookReturnsNull() {
        String isbn = "978-3-16-148410-0";
        when(bookRepository.findById(isbn)).thenReturn(Optional.empty());
        Book fetchedBook = bookService.getBook(isbn);
        assertNull(fetchedBook);
    }

    // Test 6: Save and verify the saved book
    @Test
    public void BookService_SaveAndVerify_VerifiesSavedBook() {
        Book book = new Book();
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);
        Book savedBook = bookService.saveBook(book);
        verify(bookRepository, times(1)).save(book);
        assertEquals(book, savedBook);
    }

    // Test 7: Delete non-existing Book
    @Test
    public void BookService_Delete_NonExistingBookReturnsFalse() {
        String isbn = "978-3-16-148410-0";
        when(bookRepository.findById(isbn)).thenReturn(Optional.empty());
        boolean isDeleted = bookService.deleteBook(isbn);
        assertFalse(isDeleted);
    }

    // Test 8: Try to update non-existing Book
    @Test
    public void BookService_Update_NonExistingBookReturnsNull() {
        String isbn = "978-3-16-148410-0";
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Test Book");
        updatedBook.setAuthor("Updated Test Author");
        when(bookRepository.findById(isbn)).thenReturn(Optional.empty());
        Book newBook = bookService.updateBook(isbn, updatedBook);
        assertNull(newBook);
    }

    // Test 9: Save null Book
    @Test
    public void BookService_Save_NullBookThrowsException() {
        Book book = null;
        assertThrows(IllegalArgumentException.class, () -> bookService.saveBook(book));
    }

    // Test 10: Update Book with null values
    @Test
    public void BookService_Update_BookWithNullValuesUpdatesNullValues() {
        String isbn = "978-3-16-148410-0";
        Book book = new Book();
        book.setIsbn(isbn);
        when(bookRepository.findById(isbn)).thenReturn(Optional.of(book));
        Book updatedBook = new Book();
        updatedBook.setTitle(null);
        updatedBook.setAuthor(null);
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        Book newBook = bookService.updateBook(isbn, updatedBook);
        assertNull(newBook.getTitle());
        assertNull(newBook.getAuthor());
    }

}
