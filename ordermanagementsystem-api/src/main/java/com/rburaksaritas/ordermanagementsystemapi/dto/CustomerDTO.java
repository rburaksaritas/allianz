package com.rburaksaritas.ordermanagementsystemapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Integer id;
    private String name;
    private String location;
    private String phone;
    private String mail;
    private String birthDate;
    private String password;
    private Double walletBalance;
    private Date timestamp;
}
