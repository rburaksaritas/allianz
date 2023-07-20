package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.exception.ResourceNotFoundException;
import com.rburaksaritas.booklendingapi.model.*;
import com.rburaksaritas.booklendingapi.repository.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    CardRepository cardRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void PaymentService_Get_ValidPaymentReturnsPayment() {
        Payment payment = new Payment();
        payment.setId(1L);

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        Payment result = paymentService.getPayment(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void PaymentService_Get_InvalidIdThrowsException() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.getPayment(1L));
    }

    @Test
    void PaymentService_Save_ValidPaymentReturnsPayment() {
        Payment payment = new Payment();
        User user = new User();
        Book book = new Book();
        Card card = new Card();
        user.setMail("test@mail.com");
        book.setIsbn("1234");
        card.setCardNumber("1111");
        card.setBalance(500);
        book.setDailyCost(10);
        payment.setUser(user);
        payment.setBook(book);
        payment.setCard(card);
        payment.setStartDate("2023-07-18");
        payment.setEndDate("2023-07-20");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));
        when(cardRepository.findById(anyString())).thenReturn(Optional.of(card));
        when(paymentRepository.findOverlappingPayments(anyString(), anyString(), anyString())).thenReturn(Collections.emptyList());
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.savePayment(payment);

        assertNotNull(result);
        assertEquals(20, result.getCost());
    }

    @Test
    void PaymentService_Save_NonExistentUserThrowsException() {
        Payment payment = new Payment();
        User user = new User();
        user.setMail("test@mail.com");
        payment.setUser(user);

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.savePayment(payment));
    }

    @Test
    void PaymentService_Save_NonExistentBookThrowsException() {
        // Create a user
        User user = new User();
        user.setMail("testuser@mail.com");

        // Create a card
        Card card = new Card();
        card.setCardNumber("1234567890");

        // Create a book
        Book book = new Book();
        book.setIsbn("1234");

        // Create a payment
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setBook(book);
        payment.setCard(card);
        payment.setStartDate("2017-12-12");
        payment.setEndDate("2017-12-13");

        // Make the repository return an empty Optional when searching for the book
        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());

        // Try to save the payment and expect a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> paymentService.savePayment(payment));
    }


    @Test
    void PaymentService_Save_NonExistentCardThrowsException() {
        Payment payment = new Payment();
        User user = new User();
        Book book = new Book();
        Card card = new Card();
        user.setMail("test@mail.com");
        book.setIsbn("1234");
        card.setCardNumber("non-existent-card-number");
        card.setBalance(500);
        book.setDailyCost(10);
        payment.setUser(user);
        payment.setBook(book);
        payment.setCard(card);
        payment.setStartDate("2023-07-18");
        payment.setEndDate("2023-07-20");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));
        when(cardRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.savePayment(payment));
    }


    @Test
    void PaymentService_Save_BookUnavailableThrowsException() {
        Payment payment = new Payment();
        User user = new User();
        Book book = new Book();
        Card card = new Card();
        user.setMail("test@mail.com");
        book.setIsbn("1234");
        card.setCardNumber("1111");
        card.setBalance(500);
        book.setDailyCost(10);
        payment.setUser(user);
        payment.setBook(book);
        payment.setCard(card);
        payment.setStartDate("2023-07-18");
        payment.setEndDate("2023-07-20");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));
        when(cardRepository.findById(anyString())).thenReturn(Optional.of(card));
        when(paymentRepository.findOverlappingPayments(anyString(), anyString(), anyString())).thenReturn(Arrays.asList(1L, 2L, 3L));

        assertThrows(IllegalArgumentException.class, () -> paymentService.savePayment(payment));
    }


    @Test
    void PaymentService_Save_InsufficientBalanceThrowsException() {
        Payment payment = new Payment();
        User user = new User();
        Book book = new Book();
        Card card = new Card();
        user.setMail("test@mail.com");
        book.setIsbn("1234");
        card.setCardNumber("1111");
        card.setBalance(10); // Reduced balance
        book.setDailyCost(50); // Increased cost
        payment.setUser(user);
        payment.setBook(book);
        payment.setCard(card);
        payment.setStartDate("2023-07-18");
        payment.setEndDate("2023-07-20");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));
        when(cardRepository.findById(anyString())).thenReturn(Optional.of(card));
        when(paymentRepository.findOverlappingPayments(anyString(), anyString(), anyString())).thenReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> paymentService.savePayment(payment));
    }

    @Test
    void PaymentService_Update_ValidPaymentReturnsPayment() {
        Payment existingPayment = new Payment();
        Payment updatedPayment = new Payment();
        User user = new User();
        Book book = new Book();
        Card card = new Card();

        user.setMail("test@mail.com");
        book.setIsbn("1234");
        card.setCardNumber("1111");
        card.setBalance(500);
        book.setDailyCost(10);

        existingPayment.setUser(user);
        existingPayment.setBook(book);
        existingPayment.setCard(card);
        existingPayment.setStartDate("2023-07-18");
        existingPayment.setEndDate("2023-07-20");

        updatedPayment.setUser(user);
        updatedPayment.setBook(book);
        updatedPayment.setCard(card);
        updatedPayment.setStartDate("2023-07-18");
        updatedPayment.setEndDate("2023-07-22");

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(existingPayment));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));
        when(cardRepository.findById(anyString())).thenReturn(Optional.of(card));

        Payment result = paymentService.updatePayment(1L, updatedPayment);

        assertEquals("2023-07-18", result.getStartDate());
        assertEquals("2023-07-22", result.getEndDate());
        assertEquals(40.0, result.getCost());
    }



    @Test
    void PaymentService_Update_NonExistentPaymentThrowsException() {
        Payment payment = new Payment();
        payment.setId(1L);

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> paymentService.updatePayment(1L, payment));
    }

    @Test
    void PaymentService_Update_ChangeUserThrowsException() {
        Payment existingPayment = new Payment();
        User existingUser = new User();
        User newUser = new User();
        existingUser.setMail("existing@mail.com");
        newUser.setMail("new@mail.com");
        existingPayment.setUser(existingUser);

        Payment updatedPayment = new Payment();
        updatedPayment.setUser(newUser);
        updatedPayment.setBook(new Book());  // Initialize Book
        updatedPayment.setCard(new Card());  // Initialize Card

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(existingPayment));

        assertThrows(ResourceNotFoundException.class, () -> paymentService.updatePayment(1L, updatedPayment));
    }

    @Test
    void PaymentService_Update_ChangeBookThrowsException() {
        Payment existingPayment = new Payment();
        Book existingBook = new Book();
        Book newBook = new Book();
        existingBook.setIsbn("1111");
        newBook.setIsbn("2222");
        existingPayment.setBook(existingBook);

        Payment updatedPayment = new Payment();
        updatedPayment.setBook(newBook);
        updatedPayment.setUser(new User());  // Initialize User
        updatedPayment.setCard(new Card());  // Initialize Card

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(existingPayment));

        assertThrows(ResourceNotFoundException.class, () -> paymentService.updatePayment(1L, updatedPayment));
    }

    @Test
    void PaymentService_Update_ChangeCardThrowsException() {
        Payment existingPayment = new Payment();
        Card existingCard = new Card();
        Card newCard = new Card();
        existingCard.setCardNumber("1111");
        newCard.setCardNumber("2222");
        existingPayment.setCard(existingCard);

        Payment updatedPayment = new Payment();
        updatedPayment.setCard(newCard);
        updatedPayment.setUser(new User());  // Initialize User
        updatedPayment.setBook(new Book());  // Initialize Book

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(existingPayment));

        assertThrows(ResourceNotFoundException.class, () -> paymentService.updatePayment(1L, updatedPayment));
    }

    @Test
    void PaymentService_Delete_ValidPaymentReturnsTrue() {
        Payment payment = new Payment();
        payment.setId(1L);

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        assertTrue(paymentService.deletePayment(1L));
    }

    @Test
    void PaymentService_Delete_NonExistentPaymentThrowsException() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.deletePayment(1L));
    }
}
