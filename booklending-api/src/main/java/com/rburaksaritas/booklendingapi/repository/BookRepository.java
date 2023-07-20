package com.rburaksaritas.booklendingapi.repository;

import com.rburaksaritas.booklendingapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}
