package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.exception.ResourceNotFoundException;
import com.rburaksaritas.booklendingapi.model.Book;
import com.rburaksaritas.booklendingapi.model.Card;
import com.rburaksaritas.booklendingapi.model.Payment;
import com.rburaksaritas.booklendingapi.model.User;
import com.rburaksaritas.booklendingapi.repository.BookRepository;
import com.rburaksaritas.booklendingapi.repository.CardRepository;
import com.rburaksaritas.booklendingapi.repository.PaymentRepository;
import com.rburaksaritas.booklendingapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CardRepository cardRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository, BookRepository bookRepository, CardRepository cardRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public Payment savePayment(Payment payment) {
        User user = userRepository.findById(payment.getUser().getMail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "mail", payment.getUser().getMail()));

        Book book = bookRepository.findById(payment.getBook().getIsbn())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "isbn", payment.getBook().getIsbn()));

        Card card = cardRepository.findById(payment.getCard().getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", payment.getCard().getCardNumber()));

        // Checks if the book is available.
        if (!isAvailableBetweenDates(payment.getStartDate(), payment.getEndDate(), book.getIsbn())) {
            throw new IllegalArgumentException("Book is not available between the given dates.");
        }

        // Calculates total cost.
        int numberOfDays = calculateNumberOfDays(payment.getStartDate(), payment.getEndDate());
        double totalCost = calculateTotalCost(book.getDailyCost(), numberOfDays);

        // Checks if the balance is sufficient, then proceed if so.
        if (sufficientBalance(card.getBalance(), totalCost)) {
            card.setBalance(card.getBalance() - totalCost);
            cardRepository.save(card);
            payment.setUser(user);
            payment.setBook(book);
            payment.setCard(card);
            payment.setCost(totalCost);
            paymentRepository.save(payment);
            return payment;
        } else {
            throw new IllegalArgumentException("Insufficient balance in the credit card.");
        }
    }

    @Override
    public Payment updatePayment(Long paymentId, Payment payment) {
        Payment existingPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));

        User user = userRepository.findById(payment.getUser().getMail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "mail", payment.getUser().getMail()));

        Book book = bookRepository.findById(payment.getBook().getIsbn())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "isbn", payment.getBook().getIsbn()));

        Card card = cardRepository.findById(payment.getCard().getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", payment.getCard().getCardNumber()));

        if (!existingPayment.getUser().getMail().equals(payment.getUser().getMail())) {
            throw new IllegalArgumentException("User cannot be changed in an existing payment.");
        }

        if (!existingPayment.getBook().getIsbn().equals(payment.getBook().getIsbn())) {
            throw new IllegalArgumentException("Book cannot be changed in an existing payment.");
        }

        if (!existingPayment.getCard().getCardNumber().equals(payment.getCard().getCardNumber())) {
            throw new IllegalArgumentException("Card cannot be changed in an existing payment.");
        }

        if (!existingPayment.getStartDate().equals(payment.getStartDate()) || !existingPayment.getEndDate().equals(payment.getEndDate())) {
            int numberOfDays = calculateNumberOfDays(payment.getStartDate(), payment.getEndDate());
            double totalCost = calculateTotalCost(book.getDailyCost(), numberOfDays);

            if (!existingPayment.getStartDate().equals(payment.getStartDate()) || !existingPayment.getEndDate().equals(payment.getEndDate())) {
                // Return the cost to the card's balance
                existingPayment.getCard().setBalance(existingPayment.getCard().getBalance() + existingPayment.getCost());
                // Withdraw the new total cost from the card's balance
                if (card.getBalance() < totalCost) {
                    throw new IllegalArgumentException("Insufficient balance in the credit card.");
                }
                card.setBalance(card.getBalance() - totalCost);
            }

            existingPayment.setStartDate(payment.getStartDate());
            existingPayment.setEndDate(payment.getEndDate());
            existingPayment.setCost(totalCost);
        }
        paymentRepository.save(existingPayment);
        return existingPayment;
    }

    @Override
    public boolean deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));

        paymentRepository.delete(payment);
        return true;
    }

    /**
     * Calculates number of days between two dates.
     * @param startDate
     * @param endDate
     * @return number of days
     */
    private int calculateNumberOfDays(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return (int) start.until(end).getDays();
    }

    /**
     * Calculates total cost of lending book.
     * @param dailyCost
     * @param numberOfDays
     * @return
     */
    private double calculateTotalCost(double dailyCost, int numberOfDays) {
        return dailyCost * numberOfDays;
    }

    /**
     * Determines if the book is available between given dates.
     * @param startDate
     * @param endDate
     * @param bookIsbn
     * @return availability status, true if available, false otherwise.
     */
    private boolean isAvailableBetweenDates(String startDate, String endDate, String bookIsbn) {
        List<Payment> overlappingPayments = paymentRepository.findOverlappingPayments(startDate, endDate, bookIsbn);
        return overlappingPayments.isEmpty();
    }

    /***
     * Checks if the balance is sufficient for the lending payment.
     * @param balance
     * @param cost
     * @return true if sufficient balance
     */
    private boolean sufficientBalance(double balance, double cost) {
        return (balance >= cost);
    }
}

