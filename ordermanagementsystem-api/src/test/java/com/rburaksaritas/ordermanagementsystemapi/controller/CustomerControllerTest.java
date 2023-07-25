package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.CustomerDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTests {

    private CustomerController customerController;
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerService = mock(CustomerService.class);
        customerController = new CustomerController(customerService);
    }

    @Test
    void getAllCustomers_ReturnsAllCustomersSuccessfully() {
        // Arrange
        List<CustomerDTO> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(new CustomerDTO(1, "John Doe", "john@example.com", "12345", "john's location", "01-01-1990", "password123", 100.0, new Date()));
        expectedCustomers.add(new CustomerDTO(2, "Jane Smith", "jane@example.com", "67890", "jane's location", "02-02-1995", "password456", 50.0, new Date()));
        when(customerService.getAllCustomers()).thenReturn(expectedCustomers);

        // Act
        ResponseEntity<List<CustomerDTO>> responseEntity = customerController.getAllCustomers();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedCustomers.size(), responseEntity.getBody().size());
    }

    @Test
    void getCustomerById_ValidCustomerId_ReturnsCustomerSuccessfully() {
        // Arrange
        int customerId = 1;
        CustomerDTO expectedCustomer = new CustomerDTO(1, "John Doe", "john@example.com", "12345", "john's location", "01-01-1990", "password123", 100.0, new Date());
        when(customerService.getCustomerById(customerId)).thenReturn(expectedCustomer);

        // Act
        ResponseEntity<CustomerDTO> responseEntity = customerController.getCustomerById(customerId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedCustomer.getId(), responseEntity.getBody().getId());
        assertEquals(expectedCustomer.getName(), responseEntity.getBody().getName());
    }

    @Test
    void getCustomerById_CustomerNotFound_ReturnsNotFound() {
        // Arrange
        int customerId = 1;
        when(customerService.getCustomerById(customerId)).thenReturn(null);

        // Act
        ResponseEntity<CustomerDTO> responseEntity = customerController.getCustomerById(customerId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void addCustomer_ValidCustomer_ReturnsCreatedCustomer() {
        // Arrange
        CustomerDTO newCustomer = new CustomerDTO(null, "John Doe", "john@example.com", "12345", "john's location", "01-01-1990", "password123", 100.0, null);
        CustomerDTO expectedSavedCustomer = new CustomerDTO(1, "John Doe", "john@example.com", "12345", "john's location", "01-01-1990", "password123", 100.0, new Date());
        when(customerService.saveCustomer(newCustomer)).thenReturn(expectedSavedCustomer);

        // Act
        ResponseEntity<CustomerDTO> responseEntity = customerController.addCustomer(newCustomer);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedSavedCustomer.getId(), responseEntity.getBody().getId());
        assertEquals(expectedSavedCustomer.getName(), responseEntity.getBody().getName());
    }

    @Test
    void addCustomer_DuplicateCustomerEmail_ReturnsBadRequest() {
        // Arrange
        CustomerDTO newCustomer = new CustomerDTO(1, "John Doe", "john@example.com", "12345", "john's location", "01-01-1990", "password123", 100.0, new Date());
        when(customerService.saveCustomer(newCustomer)).thenThrow(new IllegalArgumentException("Customer email must be unique."));

        // Act
        ResponseEntity<CustomerDTO> responseEntity = customerController.addCustomer(newCustomer);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void updateCustomer_ValidCustomer_ReturnsUpdatedCustomer() {
        // Arrange
        int customerId = 1;
        CustomerDTO updatedCustomer = new CustomerDTO(1, "John Doe", "john@example.com", "12345", "john's location", "01-01-1990", "password123", 100.0, new Date());
        when(customerService.updateCustomer(customerId, updatedCustomer.getName(), updatedCustomer.getLocation(),
                updatedCustomer.getPhone(), updatedCustomer.getMail(), updatedCustomer.getBirthDate(),
                updatedCustomer.getPassword(), updatedCustomer.getWalletBalance())).thenReturn(updatedCustomer);

        // Act
        ResponseEntity<CustomerDTO> responseEntity = customerController.updateCustomer(customerId, updatedCustomer.getName(),
                updatedCustomer.getLocation(), updatedCustomer.getPhone(), updatedCustomer.getMail(),
                updatedCustomer.getBirthDate(), updatedCustomer.getPassword(), updatedCustomer.getWalletBalance());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedCustomer.getId(), responseEntity.getBody().getId());
        assertEquals(updatedCustomer.getName(), responseEntity.getBody().getName());
        assertEquals(updatedCustomer.getMail(), responseEntity.getBody().getMail());
    }

    @Test
    void updateCustomer_CustomerNotFound_ReturnsNotFound() {
        // Arrange
        int customerId = 1;
        CustomerDTO updatedCustomer = new CustomerDTO(1, "John Doe", "john@example.com", "12345", "john's location", "01-01-1990", "password123", 100.0, new Date());
        when(customerService.updateCustomer(customerId, updatedCustomer.getName(), updatedCustomer.getLocation(),
                updatedCustomer.getPhone(), updatedCustomer.getMail(), updatedCustomer.getBirthDate(),
                updatedCustomer.getPassword(), updatedCustomer.getWalletBalance()))
                .thenThrow(new ResourceNotFoundException("Customer", "id", customerId));

        // Act
        ResponseEntity<CustomerDTO> responseEntity = customerController.updateCustomer(customerId, updatedCustomer.getName(),
                updatedCustomer.getLocation(), updatedCustomer.getPhone(), updatedCustomer.getMail(),
                updatedCustomer.getBirthDate(), updatedCustomer.getPassword(), updatedCustomer.getWalletBalance());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteCustomer_ValidCustomerId_ReturnsNoContent() {
        // Arrange
        int customerId = 1;

        // Act
        ResponseEntity<Void> responseEntity = customerController.deleteCustomer(customerId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteCustomer_CustomerNotFound_ReturnsNotFound() {
        // Arrange
        int customerId = 1;
        doThrow(new ResourceNotFoundException("Customer", "id", customerId)).when(customerService).deleteCustomer(customerId);

        // Act
        ResponseEntity<Void> responseEntity = customerController.deleteCustomer(customerId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
