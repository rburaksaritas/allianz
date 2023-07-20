package com.rburaksaritas.ordermanagementsystemapi.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "phone", length = 20, nullable = false, unique = true)
    private String phone;

    @Column(name = "mail", length = 100, nullable = false, unique = true)
    private String mail;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "role", length = 100, nullable = false)
    private String role;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = new Date();
    }
}
