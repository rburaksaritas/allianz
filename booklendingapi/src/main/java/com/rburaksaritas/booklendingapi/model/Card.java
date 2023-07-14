package com.rburaksaritas.booklendingapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Card {

    @Id
    private String cardNumber;
    private double balance;

    public Card() {
        // Empty constructor for JPA.
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
