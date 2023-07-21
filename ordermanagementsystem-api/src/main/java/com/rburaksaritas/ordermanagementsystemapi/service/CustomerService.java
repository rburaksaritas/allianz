package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Integer id);
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(Integer id, String updatedName,
                               String updatedLocation, String updatedPhone,
                               String updatedMail, String updatedBirthDate,
                               String updatedPassword, Double updatedWalletBalance);
    void deleteCustomer(Integer id);
}
