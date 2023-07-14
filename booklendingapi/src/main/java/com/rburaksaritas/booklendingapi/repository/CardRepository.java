package com.rburaksaritas.booklendingapi.repository;

import com.rburaksaritas.booklendingapi.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
}
