package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.StaffDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
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
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        List<StaffDTO> staffList = staffService.getAllStaff();
        try{
            return new ResponseEntity<>(staffList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable Integer id) {
        StaffDTO staff = staffService.getStaffById(id);
        if (staff != null) {
            return new ResponseEntity<>(staff, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<StaffDTO> addStaff(@RequestBody StaffDTO staffDTO) {
        StaffDTO savedStaff = staffService.saveStaff(staffDTO);
        return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StaffDTO> updateStaff(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String mail,
            @RequestParam String password,
            @RequestParam String role
    ) {
        try {
            StaffDTO updatedStaff = staffService.updateStaff(id, name, phone, mail, password, role);
            return new ResponseEntity<>(updatedStaff, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Integer id) {
        try {
            staffService.deleteStaff(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
