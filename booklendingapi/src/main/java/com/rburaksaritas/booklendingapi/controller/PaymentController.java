package com.rburaksaritas.booklendingapi.controller;

import com.rburaksaritas.booklendingapi.model.Payment;
import com.rburaksaritas.booklendingapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPayment(@RequestBody Payment payment) {
        try {
            Payment savedPayment = paymentService.savePayment(payment);
            return ResponseEntity.ok("Payment with ID " + savedPayment.getId() + " is added!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add payment: " + e.getMessage());
        }
    }

    @PutMapping("/update/{paymentId}")
    public ResponseEntity<String> updatePayment(@PathVariable Long paymentId, @RequestBody Payment payment) {
        try {
            Payment updatedPayment = paymentService.updatePayment(paymentId, payment);
            return ResponseEntity.ok("Payment with ID " + updatedPayment.getId() + " is updated!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update payment: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable Long paymentId) {
        try {
            boolean result = paymentService.deletePayment(paymentId);
            if (result) {
                return ResponseEntity.ok("Payment with ID " + paymentId + " is deleted!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete payment: " + e.getMessage());
        }
    }
}
