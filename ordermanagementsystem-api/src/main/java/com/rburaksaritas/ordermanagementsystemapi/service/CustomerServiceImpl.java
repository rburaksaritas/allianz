package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.CustomerDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Customer;
import com.rburaksaritas.ordermanagementsystemapi.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        try {
            Customer savedCustomer = customerRepository.save(customer);
            return modelMapper.map(savedCustomer, CustomerDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to save the customer:" + e.getMessage());
        }
    }

    @Override
    public CustomerDTO updateCustomer(Integer id, String updatedName, String updatedLocation,
                                      String updatedPhone, String updatedMail, String updatedBirthDate,
                                      String updatedPassword, Double updatedWalletBalance) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        try {
            Customer updatedCustomer = new Customer(id, updatedName, updatedLocation,
                    updatedPhone, updatedMail, updatedBirthDate, updatedPassword,
                    updatedWalletBalance, customer.getTimestamp());

            customerRepository.save(updatedCustomer);
            return modelMapper.map(updatedCustomer, CustomerDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to update the customer:" + e.getMessage());
        }
    }

    @Override
    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to delete the customer:" + e.getMessage());
        }
    }
}
