package com.rburaksaritas.ordermanagementsystemapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer id;
    private String name;
    private Double price;
    private String thumbnail;
    private String details;
    private CategoryDTO category;
    private Integer quantity;
    private Date timestamp;
}
