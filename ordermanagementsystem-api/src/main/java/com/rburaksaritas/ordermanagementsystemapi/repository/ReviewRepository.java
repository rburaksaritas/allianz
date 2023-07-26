package com.rburaksaritas.ordermanagementsystemapi.repository;

import com.rburaksaritas.ordermanagementsystemapi.model.Customer;
import com.rburaksaritas.ordermanagementsystemapi.model.Product;
import com.rburaksaritas.ordermanagementsystemapi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByProduct(Product product);
    List<Review> findByCustomer(Customer customer);
}
