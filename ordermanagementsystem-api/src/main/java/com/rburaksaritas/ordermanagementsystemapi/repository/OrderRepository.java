package com.rburaksaritas.ordermanagementsystemapi.repository;

import com.rburaksaritas.ordermanagementsystemapi.model.Customer;
import com.rburaksaritas.ordermanagementsystemapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomer(Customer customer);
}
