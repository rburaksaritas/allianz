package com.rburaksaritas.ordermanagementsystemapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer id;
    private CustomerDTO customer;
    private ProductDTO product;
    private Integer quantity;
    private Date orderDate;
    private Date deliveryDate;
    private String status;
}
