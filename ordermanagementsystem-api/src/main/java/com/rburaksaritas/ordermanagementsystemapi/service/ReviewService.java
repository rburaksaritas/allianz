package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    ReviewDTO getReviewById(Integer id);
    ReviewDTO saveReview(ReviewDTO reviewDTO);
    ReviewDTO updateReview(Integer id, ReviewDTO reviewDTO);
    void deleteReview(Integer id);
}
