package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.OrderDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    // TODO: Implement controller methods based on tests
    @GetMapping("/get")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return null;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDTO) {
        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Integer id,
            @RequestParam Date deliveryDate,
            @RequestParam String status
    ) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        return null;
    }
}
