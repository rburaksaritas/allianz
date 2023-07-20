package com.rburaksaritas.ordermanagementsystemapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    private Integer id;
    private String name;
    private String phone;
    private String mail;
    private String password;
    private String role;
    private Date timestamp;
}
