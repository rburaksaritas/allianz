package com.rburaksaritas.ordermanagementsystemapi.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 200, nullable = false, unique = true)
    private String name;

    @Column(name = "details", length = 500, nullable = true)
    private String details;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = new Date();
    }
}
