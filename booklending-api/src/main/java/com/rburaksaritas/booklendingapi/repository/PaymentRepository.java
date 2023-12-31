package com.rburaksaritas.booklendingapi.repository;

import com.rburaksaritas.booklendingapi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Query that finds the list of overlapping payments to see if the book is available between given dates.
     * @param bookIsbn isbn of the book of interest
     * @param startDate of lending
     * @param endDate of lending
     * @return List of payments
     */
    @Query("SELECT p.id FROM Payment p WHERE p.book.isbn = :bookIsbn " +
            "AND ((:startDate <= p.startDate AND :endDate >= p.startDate) " +
            "OR (:startDate <= p.endDate AND :endDate >= p.endDate) " +
            "OR (:startDate <= p.startDate AND :endDate >= p.startDate) " +
            "OR (:startDate >= p.startDate AND :endDate <= p.endDate))")
    List<Long> findOverlappingPayments(String bookIsbn, String startDate, String endDate);

}
