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

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Book book) {
        try{
            bookService.saveBook(book);
            return ResponseEntity.ok("New book is added!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add book: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) {
        try {
            boolean deleted = bookService.deleteBook(isbn);
            if (deleted) {
                return ResponseEntity.ok("Book deleted successfully!");
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
                return ResponseEntity.ok("Book is updated!" + updatedBook);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
