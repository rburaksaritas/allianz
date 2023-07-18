package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.Card;
import com.rburaksaritas.booklendingapi.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceImplTest {

    private CardServiceImpl cardService;
    private CardRepository cardRepository;
    private Card card;

    @BeforeEach
    public void setup() {
        cardRepository = mock(CardRepository.class);
        cardService = new CardServiceImpl(cardRepository);

        card = new Card();
        card.setCardNumber("123456");
        card.setBalance(100.0);
    }

    @Test
    public void CardService_Get_ReturnsCard() {
        when(cardRepository.findById(anyString())).thenReturn(Optional.of(card));

        Card result = cardService.getCard("123456");
        assertEquals(card, result);
    }

    @Test
    public void CardService_Get_NonExistingCardReturnsNull() {
        when(cardRepository.findById(anyString())).thenReturn(Optional.empty());

        Card result = cardService.getCard("789101");
        assertNull(result);
    }

    @Test
    public void CardService_Save_ReturnsSavedCard() {
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.saveCard(card);
        assertEquals(card, result);
    }

    @Test
    public void CardService_Update_ExistingCardUpdatesBalance() {
        when(cardRepository.findById(anyString())).thenReturn(Optional.of(card));

        Double newBalance = 200.0;
        Card result = cardService.updateCard("123456", newBalance);

        assertEquals(newBalance, result.getBalance());
    }

    @Test
    public void CardService_Update_NonExistingCardReturnsNull() {
        when(cardRepository.findById(anyString())).thenReturn(Optional.empty());

        Card result = cardService.updateCard("789101", 200.0);
        assertNull(result);
    }

    @Test
    public void CardService_Delete_CallsDeleteById() {
        doNothing().when(cardRepository).deleteById(anyString());

        cardService.deleteCard("123456");
        verify(cardRepository, times(1)).deleteById("123456");
    }

    @Test
    public void CardService_Save_NullCardThrowsException() {
        Card nullCard = null;
        assertThrows(IllegalArgumentException.class, () -> cardService.saveCard(nullCard));
    }

    @Test
    public void CardService_Update_NullCardNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.updateCard(null, 200.0));
    }

    @Test
    public void CardService_Delete_NullCardNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.deleteCard(null));
    }

    @Test
    public void CardService_Update_NegativeBalanceThrowsException() {
        when(cardRepository.findById(anyString())).thenReturn(Optional.of(card));
        Double negativeBalance = -100.0;

        assertThrows(IllegalArgumentException.class, () -> cardService.updateCard("123456", negativeBalance));
    }
}
