package com.rburaksaritas.ordermanagementsystemapi.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "thumbnail", length = 500, nullable = true)
    private String thumbnail;

    @Column(name = "details", length = 1000, nullable = true)
    private String details;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = new Date();
    }
}
