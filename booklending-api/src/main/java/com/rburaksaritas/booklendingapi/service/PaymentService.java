package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.Payment;

public interface PaymentService {
    Payment getPayment(Long id);
    Payment savePayment(Payment payment);
    Payment updatePayment(Long id, Payment payment);
    boolean deletePayment(Long id);
}
