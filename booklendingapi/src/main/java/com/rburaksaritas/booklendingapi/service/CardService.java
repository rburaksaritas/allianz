package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.Card;

public interface CardService {
    Card getCard(String cardNumber);
    Card saveCard(Card card);
    void deleteCard(String cardNumber);
}
