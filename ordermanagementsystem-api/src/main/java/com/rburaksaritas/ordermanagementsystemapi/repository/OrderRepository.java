package com.rburaksaritas.ordermanagementsystemapi.repository;

import com.rburaksaritas.ordermanagementsystemapi.model.Order;
import com.rburaksaritas.ordermanagementsystemapi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomerId(Integer customerId);
}
