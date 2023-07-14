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
    @Query("SELECT p FROM Payment p WHERE p.book.isbn = :bookIsbn " +
            "AND ((STR_TO_DATE(:startDate, '%d-%m-%Y') <= STR_TO_DATE(p.startDate, '%d-%m-%Y') AND STR_TO_DATE(:endDate, '%d-%m-%Y') >= STR_TO_DATE(p.startDate, '%d-%m-%Y')) " +
            "OR (STR_TO_DATE(:startDate, '%d-%m-%Y') <= STR_TO_DATE(p.endDate, '%d-%m-%Y') AND STR_TO_DATE(:endDate, '%d-%m-%Y') >= STR_TO_DATE(p.endDate, '%d-%m-%Y')) " +
            "OR (STR_TO_DATE(:startDate, '%d-%m-%Y') <= STR_TO_DATE(p.startDate, '%d-%m-%Y') AND STR_TO_DATE(:endDate, '%d-%m-%Y') >= STR_TO_DATE(p.startDate, '%d-%m-%Y')) " +
            "OR (STR_TO_DATE(p.startDate, '%d-%m-%Y') >= STR_TO_DATE(:startDate, '%d-%m-%Y') AND STR_TO_DATE(p.endDate, '%d-%m-%Y') <= STR_TO_DATE(:endDate, '%d-%m-%Y')))")
    List<Payment> findOverlappingPayments(String startDate, String endDate, String bookIsbn);

}
