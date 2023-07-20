package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.Card;

public interface CardService {
    Card getCard(String cardNumber);
    Card saveCard(Card card);
    Card updateCard(String cardNumber, Double balance);
    void deleteCard(String cardNumber);
}
