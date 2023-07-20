package com.rburaksaritas.ordermanagementsystemapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Integer id;
    private String description;
    private Integer star;
    private CustomerDTO customer;
    private ProductDTO product;
    private Date timestamp;
}
