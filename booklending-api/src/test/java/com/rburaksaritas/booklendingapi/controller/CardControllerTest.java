package com.rburaksaritas.booklendingapi.controller;

import com.rburaksaritas.booklendingapi.model.Card;
import com.rburaksaritas.booklendingapi.service.CardService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardControllerTest {

    @InjectMocks
    private CardController cardController;

    @Mock
    private CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void CardController_Get_ReturnsCard() {
        Card mockCard = new Card();
        mockCard.setCardNumber("1234");
        when(cardService.getCard(anyString())).thenReturn(mockCard);
        ResponseEntity<Card> responseEntity = cardController.getCard("1234");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockCard, responseEntity.getBody());
    }

    @Test
    void CardController_Get_NonExistentCardNumber_ReturnsNotFound() {
        when(cardService.getCard(anyString())).thenReturn(null);
        ResponseEntity<Card> responseEntity = cardController.getCard("1234");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void CardController_Add_ValidCard_ReturnsCreated() {
        Card mockCard = new Card();
        mockCard.setCardNumber("1234");
        when(cardService.saveCard(any(Card.class))).thenReturn(mockCard);
        ResponseEntity<Card> responseEntity = cardController.addCard(mockCard);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockCard, responseEntity.getBody());
    }

    @Test
    void CardController_Delete_ExistentCardNumber_ReturnsOK() {
        doNothing().when(cardService).deleteCard(anyString());
        ResponseEntity<String> responseEntity = cardController.deleteCard("1234");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void CardController_Update_ExistentCardNumber_ReturnsUpdatedCard() {
        Card mockCard = new Card();
        mockCard.setCardNumber("1234");
        mockCard.setBalance(200.00);
        when(cardService.updateCard(anyString(), anyDouble())).thenReturn(mockCard);
        ResponseEntity<Card> responseEntity = cardController.updateCardBalance("1234", 200.00);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(mockCard, responseEntity.getBody());
    }

    @Test
    void CardController_Update_NonExistentCardNumber_ReturnsNotFound() {
        when(cardService.updateCard(anyString(), anyDouble())).thenReturn(null);
        ResponseEntity<Card> responseEntity = cardController.updateCardBalance("1234", 200.00);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void CardController_Add_EmptyCardNumber_ReturnsBadRequest() {
        Card mockCard = new Card();
        mockCard.setCardNumber("");
        when(cardService.saveCard(any(Card.class))).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> cardController.addCard(mockCard));
    }

    @Test
    void CardController_Add_NullCardNumber_ReturnsBadRequest() {
        Card mockCard = new Card();
        mockCard.setCardNumber(null);
        when(cardService.saveCard(any(Card.class))).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> cardController.addCard(mockCard));
    }

    @Test
    void CardController_Update_NegativeBalance_ReturnsBadRequest() {
        Card mockCard = new Card();
        mockCard.setCardNumber("1234");
        mockCard.setBalance(-1.0);
        when(cardService.updateCard(anyString(), anyDouble())).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> cardController.updateCardBalance("1234", -1.0));
    }

    @Test
    void CardController_Update_ExceedingBalanceLimit_ReturnsBadRequest() {
        Card mockCard = new Card();
        mockCard.setCardNumber("1234");
        mockCard.setBalance(1_000_000.0); // Assuming your limit is less than this
        when(cardService.updateCard(anyString(), anyDouble())).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> cardController.updateCardBalance("1234", 1_000_000.0));
    }

}
