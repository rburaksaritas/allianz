package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.Book;
import com.rburaksaritas.booklendingapi.respository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book) ;
    }
}
