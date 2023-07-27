package com.rburaksaritas.ordermanagementsystemapi.repository;

import com.rburaksaritas.ordermanagementsystemapi.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
}
