package com.rburaksaritas.booklendingapi.respository;

import com.rburaksaritas.booklendingapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
