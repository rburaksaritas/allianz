package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.ReviewDTO;
import com.rburaksaritas.ordermanagementsystemapi.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        return null;
    }

    @Override
    public ReviewDTO getReviewById(Integer id) {
        return null;
    }

    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        return null;
    }

    @Override
    public ReviewDTO updateReview(Integer id, Integer updatedStar, String updatedDescription) {
        return null;
    }

    @Override
    public void deleteReview(Integer id) {

    }
}
