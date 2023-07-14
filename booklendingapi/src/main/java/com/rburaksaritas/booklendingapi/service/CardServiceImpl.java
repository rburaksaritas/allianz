package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.Card;
import com.rburaksaritas.booklendingapi.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card getCard(String cardNumber) {
        return cardRepository.findById(cardNumber).orElse(null);
    }

    @Override
    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card updateCard(String cardNumber, Double balance) {
        Card existingCard = cardRepository.findById(cardNumber).orElse(null);
        if (existingCard != null) {
            existingCard.setBalance(balance);
            return cardRepository.save(existingCard);
        }
        return null;
    }

    @Override
    public void deleteCard(String cardNumber) {
        cardRepository.deleteById(cardNumber);
    }
}
