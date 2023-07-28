package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.CustomerDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.ProductDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.ReviewDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Category;
import com.rburaksaritas.ordermanagementsystemapi.model.Customer;
import com.rburaksaritas.ordermanagementsystemapi.model.Product;
import com.rburaksaritas.ordermanagementsystemapi.model.Review;
import com.rburaksaritas.ordermanagementsystemapi.repository.CustomerRepository;
import com.rburaksaritas.ordermanagementsystemapi.repository.OrderRepository;
import com.rburaksaritas.ordermanagementsystemapi.repository.ProductRepository;
import com.rburaksaritas.ordermanagementsystemapi.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTests {

    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private ReviewService reviewService;
    private ReviewRepository reviewRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        customerRepository = mock(CustomerRepository.class);
        orderRepository = mock(OrderRepository.class);
        reviewRepository = mock(ReviewRepository.class);

        modelMapper = new ModelMapper();
        reviewService = new ReviewServiceImpl(productRepository, customerRepository, orderRepository, reviewRepository, modelMapper);
    }

    // ReviewService Tests
    @Test
    public void ReviewService_GetAll_ReturnsAllReviews() {
        // Arrange
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(1, "Review 1", 5, new Customer(), new Product(), new Date()));
        reviews.add(new Review(2, "Review 2", 4, new Customer(), new Product(), new Date()));
        when(reviewRepository.findAll()).thenReturn(reviews);

        // Act
        List<ReviewDTO> reviewDTOList = reviewService.getAllReviews();

        // Assert
        assertNotNull(reviewDTOList);
        assertEquals(reviews.size(), reviewDTOList.size());
    }

    @Test
    public void ReviewService_GetById_ValidReviewReturnsReview() {
        // Arrange
        int reviewId = 1;
        Review review = new Review(reviewId, "Test Review", 4, new Customer(), new Product(), new Date());
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        // Act
        ReviewDTO reviewDTO = reviewService.getReviewById(reviewId);

        // Assert
        assertNotNull(reviewDTO);
        assertEquals(review.getId(), reviewDTO.getId());
        assertEquals(review.getDescription(), reviewDTO.getDescription());
    }

    @Test
    public void ReviewService_GetById_ReviewNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int reviewId = 1;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.getReviewById(reviewId));
    }

    @Test
    public void ReviewService_SaveReview_ValidReviewDTO_ReturnsSavedReviewDTO() {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setDescription("Test Review");
        reviewDTO.setStar(4);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1);
        reviewDTO.setCustomer(customerDTO);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1);
        reviewDTO.setProduct(productDTO);

        Review review = modelMapper.map(reviewDTO, Review.class);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(customerRepository.findById(1)).thenReturn(Optional.of(new Customer())); // Mock customer repository call
        when(productRepository.findById(1)).thenReturn(Optional.of(new Product())); // Mock product repository call
        when(orderRepository.existsByCustomerAndProduct(any(Customer.class), any(Product.class))).thenReturn(true); // Mock order repository call

        // Act
        ReviewDTO savedReviewDTO = reviewService.saveReview(reviewDTO);

        // Assert
        assertNotNull(savedReviewDTO);
        assertEquals(review.getDescription(), savedReviewDTO.getDescription());

    }

    @Test
    public void ReviewService_UpdateReview_ValidReviewIdAndData_ReturnsUpdatedReviewDTO() {
        // Arrange
        int reviewId = 1;
        int updatedStar = 5;
        String updatedDescription = "Updated Review";
        ReviewDTO reviewDTO = new ReviewDTO(reviewId, updatedDescription, updatedStar, new CustomerDTO(), new ProductDTO(), new Date());
        Review existingReview = new Review(reviewId, "Test Review", 4, new Customer(), new Product(), new Date());
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(any())).thenReturn(existingReview);

        // Act
        ReviewDTO updatedReviewDTO = reviewService.updateReview(reviewId, updatedStar, updatedDescription);

        // Assert
        assertNotNull(updatedReviewDTO);
        assertEquals(reviewId, updatedReviewDTO.getId());
        assertEquals(updatedDescription, updatedReviewDTO.getDescription());
    }

    @Test
    public void ReviewService_UpdateReview_ReviewNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int reviewId = 1;
        int updatedStar = 5;
        String updatedDescription = "Updated Review";
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.updateReview(reviewId, updatedStar, updatedDescription));
    }

    @Test
    public void ReviewService_Delete_ValidReviewId_DeletesReview() {
        // Arrange
        int reviewId = 1;
        Review existingReview = new Review(reviewId, "Test Review", 4, new Customer(), new Product(), new Date());
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        // Act
        assertDoesNotThrow(() -> reviewService.deleteReview(reviewId));

        // Assert
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    public void ReviewService_Delete_ReviewNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int reviewId = 1;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.deleteReview(reviewId));
    }

    @Test
    public void ReviewService_GetReviewsOfProduct_ValidProductId_ReturnsReviewsOfProduct() {
        // Arrange
        int productId = 1;
        Product product = new Product();
        product.setId(productId);
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(1, "Review 1", 5, new Customer(), product, new Date()));
        reviews.add(new Review(2, "Review 2", 4, new Customer(), product, new Date()));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(reviewRepository.findByProduct(product)).thenReturn(reviews);

        // Act
        List<ReviewDTO> reviewDTOList = reviewService.getReviewsOfProduct(productId);

        // Assert
        assertNotNull(reviewDTOList);
        assertEquals(reviews.size(), reviewDTOList.size());
    }

    @Test
    public void ReviewService_GetReviewsOfProduct_InvalidProductIdThrowsResourceNotFoundException() {
        // Arrange
        int productId = 1;
        List<Product> products = new ArrayList<>();
        when(productRepository.findById(productId)).thenThrow(new ResourceNotFoundException("product", "id", productId));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.getReviewsOfProduct(productId));
    }

    @Test
    public void ReviewService_GetAverageReviewOfProduct_ValidProductId_ReturnsAverageReview() {
        // Arrange
        int productId = 1;
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(productId);
        product2.setId(productId);
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(1, "Review 1", 5, new Customer(), product1, new Date()));
        reviews.add(new Review(2, "Review 2", 4, new Customer(), product2, new Date()));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product1));
        when(reviewRepository.findByProduct(product1)).thenReturn(reviews);

        // Act
        double averageReview = reviewService.getAverageReviewOfProduct(productId);

        // Assert
        assertEquals(4.5, averageReview);
    }

    @Test
    public void ReviewService_GetAverageReviewOfProduct_InvalidProductIdThrowsResourceNotFoundException() {
        // Arrange
        int productId = 1;
        Product product = new Product();
        when(reviewRepository.findByProduct(product)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.getAverageReviewOfProduct(productId));
    }

    @Test
    public void ReviewService_GetReviewsOfCustomer_ValidCustomerId_ReturnsReviewsOfCustomer() {
        // Arrange
        int customerId = 1;
        Customer customer = new Customer();
        customer.setId(customerId);
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(1, "Review 1", 5, customer, new Product(), new Date()));
        reviews.add(new Review(2, "Review 2", 4, customer, new Product(), new Date()));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(reviewRepository.findByCustomer(customer)).thenReturn(reviews);

        // Act
        List<ReviewDTO> reviewDTOList = reviewService.getReviewsOfCustomer(customerId);

        // Assert
        assertNotNull(reviewDTOList);
        assertEquals(reviews.size(), reviewDTOList.size());
    }

    @Test
    public void ReviewService_GetReviewsOfCustomer_InvalidCustomerIdThrowsResourceNotFoundException() {
        // Arrange
        int customerId = 1;
        Customer customer = new Customer();
        when(reviewRepository.findByCustomer(customer)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.getReviewsOfCustomer(customerId));
    }
}
