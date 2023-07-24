package com.rburaksaritas.ordermanagementsystemapi.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "order_date", nullable = false, updatable = false)
    private Date orderDate;

    @Column(name = "delivery_date", nullable = true)
    private Date deliveryDate;

    @Column(name = "status", length = 100, nullable = false)
    private String status;

    @PrePersist
    protected void onCreate() {
        this.orderDate = new Date();
        this.status = "Created";
    }
}
