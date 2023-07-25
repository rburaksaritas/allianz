package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.CustomerDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.ProductDTO;
import com.rburaksaritas.ordermanagementsystemapi.dto.ReviewDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewControllerTests {

    private ReviewController reviewController;
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        reviewService = mock(ReviewService.class);
        reviewController = new ReviewController(reviewService);
    }

    @Test
    void getAllReviews_ReturnsAllReviewsSuccessfully() {
        // Arrange
        List<ReviewDTO> expectedReviews = new ArrayList<>();
        expectedReviews.add(new ReviewDTO(1, "Great product!", 5, new CustomerDTO(), new ProductDTO(), new Date()));
        expectedReviews.add(new ReviewDTO(2, "Good quality.", 4, new CustomerDTO(), new ProductDTO(), new Date()));
        when(reviewService.getAllReviews()).thenReturn(expectedReviews);

        // Act
        ResponseEntity<List<ReviewDTO>> responseEntity = reviewController.getAllReviews();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedReviews.size(), responseEntity.getBody().size());
    }

    @Test
    void getReviewById_ValidReviewId_ReturnsReviewSuccessfully() {
        // Arrange
        int reviewId = 1;
        ReviewDTO expectedReview = new ReviewDTO(reviewId, "Excellent product!", 5, new CustomerDTO(), new ProductDTO(), new Date());
        when(reviewService.getReviewById(reviewId)).thenReturn(expectedReview);

        // Act
        ResponseEntity<ReviewDTO> responseEntity = reviewController.getReviewById(reviewId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedReview.getId(), responseEntity.getBody().getId());
        assertEquals(expectedReview.getDescription(), responseEntity.getBody().getDescription());
    }

    @Test
    void getReviewById_ReviewNotFound_ReturnsNotFound() {
        // Arrange
        int reviewId = 1;
        when(reviewService.getReviewById(reviewId)).thenReturn(null);

        // Act
        ResponseEntity<ReviewDTO> responseEntity = reviewController.getReviewById(reviewId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void addReview_ValidReview_ReturnsCreatedReview() {
        // Arrange
        ReviewDTO newReview = new ReviewDTO(null, "Nice product!", 4, new CustomerDTO(), new ProductDTO(), new Date());
        ReviewDTO expectedSavedReview = new ReviewDTO(1,"Nice product!", 4, new CustomerDTO(), new ProductDTO(), new Date());
        when(reviewService.saveReview(newReview)).thenReturn(expectedSavedReview);

        // Act
        ResponseEntity<ReviewDTO> responseEntity = reviewController.addReview(newReview);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedSavedReview.getId(), responseEntity.getBody().getId());
        assertEquals(expectedSavedReview.getDescription(), responseEntity.getBody().getDescription());
    }

    @Test
    void updateReview_ValidReview_ReturnsUpdatedReview() {
        // Arrange
        int reviewId = 1;
        ReviewDTO updatedReview = new ReviewDTO(reviewId, "Average product.", 3, new CustomerDTO(), new ProductDTO(), new Date());
        when(reviewService.updateReview(reviewId, updatedReview.getStar(), updatedReview.getDescription()))
                .thenReturn(updatedReview);

        // Act
        ResponseEntity<ReviewDTO> responseEntity = reviewController.updateReview(reviewId, updatedReview.getStar(), updatedReview.getDescription());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedReview.getId(), responseEntity.getBody().getId());
        assertEquals(updatedReview.getDescription(), responseEntity.getBody().getDescription());
    }

    @Test
    void updateReview_ReviewNotFound_ReturnsNotFound() {
        // Arrange
        int reviewId = 1;
        ReviewDTO updatedReview = new ReviewDTO(reviewId, "Average product.", 3, new CustomerDTO(), new ProductDTO(), new Date());
        when(reviewService.updateReview(reviewId, updatedReview.getStar(), updatedReview.getDescription()))
                .thenThrow(new ResourceNotFoundException("Review", "id", reviewId));

        // Act
        ResponseEntity<ReviewDTO> responseEntity = reviewController.updateReview(reviewId, updatedReview.getStar(), updatedReview.getDescription());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteReview_ValidReviewId_ReturnsNoContent() {
        // Arrange
        int reviewId = 1;

        // Act
        ResponseEntity<Void> responseEntity = reviewController.deleteReview(reviewId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteReview_ReviewNotFound_ReturnsNotFound() {
        // Arrange
        int reviewId = 1;
        doThrow(new ResourceNotFoundException("Review", "id", reviewId)).when(reviewService).deleteReview(reviewId);

        // Act
        ResponseEntity<Void> responseEntity = reviewController.deleteReview(reviewId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
