package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.User;
import com.rburaksaritas.booklendingapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(String mail) {
        return (userRepository.findById(mail).orElse(null));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String mail, User user) {
        User existingUser = userRepository.findById(mail).orElse(null);
        if (existingUser != null) {
            existingUser.setPassword(user.getPassword());
            existingUser.setActive(user.isActive());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public boolean deleteUser(String mail) {
        User existingUser = userRepository.findById(mail).orElse(null);
        if (existingUser != null) {
            userRepository.delete(existingUser);
            return true;
        }
        return false;
    }
}
