package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.CustomerDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Customer;
import com.rburaksaritas.ordermanagementsystemapi.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTests {

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        customerRepository = mock(CustomerRepository.class);
        modelMapper = new ModelMapper();
        customerService = new CustomerServiceImpl(customerRepository, modelMapper);
    }

    // CustomerService Tests
    @Test
    public void CustomerService_GetAll_ReturnsAllCustomers() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "Customer 1", "Location 1", "1234567890", "customer1@example.com", "1990-01-01", "password1", 100.0, new Date()));
        customers.add(new Customer(2, "Customer 2", "Location 2", "0987654321", "customer2@example.com", "1995-05-05", "password2", 200.0, new Date()));
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<CustomerDTO> customerDTOList = customerService.getAllCustomers();

        // Assert
        assertNotNull(customerDTOList);
        assertEquals(customers.size(), customerDTOList.size());
    }

    @Test
    public void CustomerService_GetCustomerById_ValidCustomerReturnsCustomer() {
        // Arrange
        int customerId = 1;
        Customer customer = new Customer(1, "Test Customer", "Test Location", "1234567890", "test@example.com", "1990-01-01", "test123", 100.0, new Date());
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);

        // Assert
        assertNotNull(customerDTO);
        assertEquals(customer.getId(), customerDTO.getId());
        assertEquals(customer.getName(), customerDTO.getName());
    }

    @Test
    public void CustomerService_GetCustomerById_CustomerNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int customerId = 1;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(customerId));
    }

    @Test
    public void CustomerService_SaveCustomer_ValidCustomerDTO_ReturnsSavedCustomerDTO() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Test Customer");
        customerDTO.setLocation("Test Location");
        customerDTO.setPhone("1234567890");
        customerDTO.setMail("test@example.com");
        customerDTO.setBirthDate("1990-01-01");
        customerDTO.setPassword("test123");
        customerDTO.setWalletBalance(100.0);

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        CustomerDTO savedCustomerDTO = customerService.saveCustomer(customerDTO);

        // Assert
        assertNotNull(savedCustomerDTO);
        assertEquals(customer.getId(), savedCustomerDTO.getId());
        assertEquals(customer.getName(), savedCustomerDTO.getName());
    }

    @Test
    public void CustomerService_UpdateCustomer_ValidCustomerIdAndData_ReturnsUpdatedCustomerDTO() {
        // Arrange
        int customerId = 1;
        String updatedName = "Updated Customer";
        String updatedLocation = "Updated Location";
        String updatedPhone = "0987654321";
        String updatedMail = "updated@example.com";
        String updatedBirthDate = "1995-05-05";
        String updatedPassword = "updated123";
        Double updatedWalletBalance = 200.0;

        Customer customer = new Customer(customerId, "Test Customer", "Test Location", "1234567890", "test@example.com", "1990-01-01", "test123", 100.0, new Date());
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        CustomerDTO updatedCustomerDTO = customerService.updateCustomer(customerId, updatedName, updatedLocation, updatedPhone, updatedMail, updatedBirthDate, updatedPassword, updatedWalletBalance);

        // Assert
        assertNotNull(updatedCustomerDTO);
        assertEquals(customerId, updatedCustomerDTO.getId());
        assertEquals(updatedName, updatedCustomerDTO.getName());
        assertEquals(updatedLocation, updatedCustomerDTO.getLocation());
        assertEquals(updatedPhone, updatedCustomerDTO.getPhone());
        assertEquals(updatedMail, updatedCustomerDTO.getMail());
        assertEquals(updatedBirthDate, updatedCustomerDTO.getBirthDate());
        assertEquals(updatedPassword, updatedCustomerDTO.getPassword());
        assertEquals(updatedWalletBalance, updatedCustomerDTO.getWalletBalance());
    }

    @Test
    public void CustomerService_UpdateCustomer_CustomerNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int customerId = 1;
        String updatedName = "Updated Customer";
        String updatedLocation = "Updated Location";
        String updatedPhone = "0987654321";
        String updatedMail = "updated@example.com";
        String updatedBirthDate = "1995-05-05";
        String updatedPassword = "updated123";
        Double updatedWalletBalance = 200.0;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(customerId, updatedName, updatedLocation, updatedPhone, updatedMail, updatedBirthDate, updatedPassword, updatedWalletBalance));
    }

    @Test
    public void CustomerService_Delete_ValidCustomerId_DeletesCustomer() {
        // Arrange
        int customerId = 1;
        Customer existingCustomer = new Customer(customerId, "Test Customer", "Test Location", "1234567890", "test@example.com", "1990-01-01", "test123", 100.0, new Date());
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        // Act
        assertDoesNotThrow(() -> customerService.deleteCustomer(customerId));

        // Assert
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    public void CustomerService_Delete_CustomerNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int customerId = 1;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(customerId));
    }
}
