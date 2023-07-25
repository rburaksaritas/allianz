package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.StaffDTO;
import com.rburaksaritas.ordermanagementsystemapi.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService){
        this.staffService = staffService;
    }
    
    // TODO: Implement controller methods based on tests
    @GetMapping("/get")
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        return null;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable Integer id) {
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<StaffDTO> addStaff(@RequestBody StaffDTO staffDTO) {
        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StaffDTO> updateStaff(
            @PathVariable Integer id,
            @RequestParam String updatedName,
            @RequestParam String updatedPhone,
            @RequestParam String updatedMail,
            @RequestParam String updatedPassword,
            @RequestParam String updatedRole
    ) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Integer id) {
        return null;
    }
}
