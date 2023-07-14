package com.rburaksaritas.booklendingapi.controller;

import com.rburaksaritas.booklendingapi.model.Card;
import com.rburaksaritas.booklendingapi.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<Card> getCard(@PathVariable String cardNumber) {
        Card card = cardService.getCard(cardNumber);
        if (card != null) {
            return ResponseEntity.ok(card);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        Card savedCard = cardService.saveCard(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }

    @DeleteMapping("/delete/{cardNumber}")
    public ResponseEntity<String> deleteCard(@PathVariable String cardNumber) {
        cardService.deleteCard(cardNumber);
        return ResponseEntity.ok("Card with number " + cardNumber + " has been deleted.");
    }
}
