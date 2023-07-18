package com.rburaksaritas.booklendingapi.controller;

import com.rburaksaritas.booklendingapi.model.Book;
import com.rburaksaritas.booklendingapi.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void BookController_Get_ReturnsBook() {
        Book mockBook = new Book();
        mockBook.setIsbn("1234");
        when(bookService.getBook(anyString())).thenReturn(mockBook);
        ResponseEntity<Book> responseEntity = bookController.getBook("1234");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockBook, responseEntity.getBody());
    }

    @Test
    void BookController_Get_NonExistentIsbn_ReturnsNotFound() {
        when(bookService.getBook(anyString())).thenReturn(null);
        ResponseEntity<Book> responseEntity = bookController.getBook("1234");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void BookController_Add_ValidBook_ReturnsCreated() {
        Book mockBook = new Book();
        mockBook.setIsbn("1234");
        when(bookService.saveBook(any(Book.class))).thenReturn(mockBook);
        ResponseEntity<Book> responseEntity = bookController.add(mockBook);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockBook, responseEntity.getBody());
    }

    @Test
    void BookController_Delete_ExistentIsbn_ReturnsOK() {
        when(bookService.deleteBook(anyString())).thenReturn(true);
        ResponseEntity<String> responseEntity = bookController.deleteBook("1234");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void BookController_Delete_NonExistentIsbn_ReturnsNotFound() {
        when(bookService.deleteBook(anyString())).thenReturn(false);
        ResponseEntity<String> responseEntity = bookController.deleteBook("1234");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void BookController_Update_ExistentIsbn_ReturnsUpdatedBook() {
        Book mockBook = new Book();
        mockBook.setIsbn("1234");
        when(bookService.updateBook(anyString(), any(Book.class))).thenReturn(mockBook);
        ResponseEntity<Book> responseEntity = bookController.updateBook("1234", mockBook);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockBook, responseEntity.getBody());
    }

    @Test
    void BookController_Update_NonExistentIsbn_ReturnsNotFound() {
        when(bookService.updateBook(anyString(), any(Book.class))).thenReturn(null);
        ResponseEntity<Book> responseEntity = bookController.updateBook("1234", new Book());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
