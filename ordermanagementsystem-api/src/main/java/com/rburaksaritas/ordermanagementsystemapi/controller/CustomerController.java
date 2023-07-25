package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.CustomerDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        try {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO savedCustomer = customerService.saveCustomer(customerDTO);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Integer id,
            @RequestParam String updatedName,
            @RequestParam String updatedLocation,
            @RequestParam String updatedPhone,
            @RequestParam String updatedMail,
            @RequestParam String updatedBirthDate,
            @RequestParam String updatedPassword,
            @RequestParam Double updatedWalletBalance
    ) {
        try {
            CustomerDTO updatedCustomer = customerService.updateCustomer(id, updatedName, updatedLocation,
                    updatedPhone, updatedMail, updatedBirthDate, updatedPassword, updatedWalletBalance);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        try {
            customerService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
