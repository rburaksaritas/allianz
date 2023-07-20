package com.rburaksaritas.booklendingapi.controller;

import com.rburaksaritas.booklendingapi.exception.ResourceNotFoundException;
import com.rburaksaritas.booklendingapi.model.Payment;
import com.rburaksaritas.booklendingapi.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private Payment payment;

    @BeforeEach
    public void setUp() {
        payment = new Payment();
    }

    @Test
    public void getPayment_ValidId_ReturnsPayment() {
        // Arrange
        when(paymentService.getPayment(anyLong())).thenReturn(payment);

        // Act
        ResponseEntity<Payment> response = paymentController.getPayment(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(payment, response.getBody());
    }

    @Test
    public void getPayment_InvalidId_ThrowsException() {
        // Arrange
        when(paymentService.getPayment(anyLong())).thenThrow(new ResourceNotFoundException("Payment", "id", 1L));

        // Act & Assert
        ResponseEntity<Payment> responseEntity = paymentController.getPayment(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void addPayment_ValidPayment_AddsPayment() {
        // Arrange
        when(paymentService.savePayment(any(Payment.class))).thenReturn(payment);

        // Act
        ResponseEntity<String> response = paymentController.addPayment(payment);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("is added!"));
    }

    @Test
    public void updatePayment_ValidPaymentAndId_UpdatesPayment() {
        // Arrange
        when(paymentService.updatePayment(anyLong(), any(Payment.class))).thenReturn(payment);

        // Act
        ResponseEntity<String> response = paymentController.updatePayment(1L, payment);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("is updated!"));
    }

    @Test
    public void deletePayment_ValidId_DeletesPayment() {
        // Arrange
        when(paymentService.deletePayment(anyLong())).thenReturn(true);

        // Act
        ResponseEntity<String> response = paymentController.deletePayment(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("is deleted!"));
    }

    @Test
    public void deletePayment_InvalidId_ReturnsNotFound() {
        // Arrange
        when(paymentService.deletePayment(anyLong())).thenReturn(false);

        // Act
        ResponseEntity<String> response = paymentController.deletePayment(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
