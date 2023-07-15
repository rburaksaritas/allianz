package com.rburaksaritas.booklendingapi.controller;

import com.rburaksaritas.booklendingapi.model.Book;
import com.rburaksaritas.booklendingapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{isbn}")
    public ResponseEntity<String> getBook(@PathVariable String isbn) {
        try {
            Book book = bookService.getBook(isbn);
            if (book != null) {
                return ResponseEntity.ok("Book with ISBN " + isbn + ":\n" +
                        "Author: " + book.getAuthor() + "\n" +
                        "Title: " + book.getTitle() + "\n" +
                        "Daily Cost: " + book.getDailyCost());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Book book) {
        try {
            bookService.saveBook(book);
            return ResponseEntity.ok("New book is added!\n" +
                    "ISBN: " + book.getIsbn() + "\n" +
                    "Author: " + book.getAuthor() + "\n" +
                    "Title: " + book.getTitle() + "\n" +
                    "Daily Cost: " + book.getDailyCost());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add book: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) {
        try {
            boolean deleted = bookService.deleteBook(isbn);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Book with ISBN " + isbn + " has been deleted.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete book: " + e.getMessage());
        }
    }

    @PutMapping("/update/{isbn}")
    public ResponseEntity<String> updateBook(@PathVariable String isbn, @RequestBody Book book) {
        try {
            Book updatedBook = bookService.updateBook(isbn, book);
            if (updatedBook != null) {
                return ResponseEntity.ok("Book with ISBN " + isbn + " has been updated.\n" +
                        "New Book Details:\n" +
                        "ISBN: " + updatedBook.getIsbn() + "\n" +
                        "Author: " + updatedBook.getAuthor() + "\n" +
                        "Title: " + updatedBook.getTitle() + "\n" +
                        "Daily Cost: " + updatedBook.getDailyCost());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update book: " + e.getMessage());
        }
    }
}
