package com.rburaksaritas.ordermanagementsystemapi.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "location", length = 200, nullable = true)
    private String location;

    @Column(name = "phone", length = 20, nullable = false, unique = true)
    private String phone;

    @Column(name = "mail", length = 100, nullable = false, unique = true)
    private String mail;

    @Column(name = "birth_date", nullable = true)
    private String birthDate;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "wallet_balance", nullable = false)
    private Double walletBalance;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = new Date();
    }
}
