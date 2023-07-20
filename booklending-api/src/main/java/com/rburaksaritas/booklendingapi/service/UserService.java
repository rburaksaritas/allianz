package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.User;

public interface UserService {
    User getUser(String mail);
    User saveUser(User user);
    User updateUser(String mail, User user);
    boolean deleteUser(String mail);
}
