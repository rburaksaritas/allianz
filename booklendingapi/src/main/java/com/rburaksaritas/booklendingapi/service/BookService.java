package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.Book;

public interface BookService {
    public Book saveBook(Book book);
    public boolean deleteBook(String isbn);
    public Book updateBook(String isbn, Book book);

}
