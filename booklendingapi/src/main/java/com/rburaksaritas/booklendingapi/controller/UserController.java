package com.rburaksaritas.booklendingapi.controller;

import com.rburaksaritas.booklendingapi.model.User;
import com.rburaksaritas.booklendingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{mail}")
    public ResponseEntity<String> getUser(@PathVariable String mail) {
        try {
            User user = userService.getUser(mail);
            if (user != null) {
                return ResponseEntity.ok("User with mail " + mail + ":\n" +
                        "Password: " + user.getPassword() + "\n" +
                        "Active: " + user.isActive() + "\n");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            if (savedUser != null) {
                return ResponseEntity.ok("User with mail " + savedUser.getMail() + " is added!\n" +
                        "Password: " + savedUser.getPassword() + "\n" +
                        "Active status: " + savedUser.isActive());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add the user.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/update/{mail}")
    public ResponseEntity<String> updateUser(@PathVariable String mail, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(mail, user);
            if (updatedUser != null) {
                return ResponseEntity.ok("User with mail " + mail + " is updated!\n" +
                        "New password: " + updatedUser.getPassword() + "\n" +
                        "Active status: " + updatedUser.isActive());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{mail}")
    public ResponseEntity<String> deleteUser(@PathVariable String mail) {
        try {
            boolean result = userService.deleteUser(mail);
            if (result) {
                return ResponseEntity.ok("User with mail " + mail + " is deleted!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
