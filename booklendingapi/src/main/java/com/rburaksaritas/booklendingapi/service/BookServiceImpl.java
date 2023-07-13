package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.Book;
import com.rburaksaritas.booklendingapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getBook(String isbn) {
        return (bookRepository.findById(isbn).orElse(null));
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean deleteBook(String isbn) {
        Book existingBook = bookRepository.findById(isbn).orElse(null);
        if (existingBook != null) {
            bookRepository.delete(existingBook);
            return true;
        }
        return false;
    }

    @Override
    public Book updateBook(String isbn, Book book) {
        Book existingBook = bookRepository.findById(isbn).orElse(null);
        if (existingBook != null) {
            existingBook.setAuthor(book.getAuthor());
            existingBook.setTitle(book.getTitle());
            existingBook.setDailyCost(book.getDailyCost());
            return bookRepository.save(existingBook);
        }
        return null;
    }

    // Implement other necessary methods
}

