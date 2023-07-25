package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.model.Manager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    // TODO: Implement controller methods based on tests
    @GetMapping("/get")
    public ResponseEntity<List<Manager>> getAllManagers() {
        return null;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Integer id) {
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<Manager> addManager(@RequestBody Manager manager) {
        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Manager> updateManager(
            @PathVariable Integer id,
            @RequestParam String updatedUsername,
            @RequestParam String updatedPassword
    ) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Integer id) {
        return null;
    }
}
