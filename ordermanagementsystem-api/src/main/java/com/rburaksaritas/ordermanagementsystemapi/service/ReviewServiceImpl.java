package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.ReviewDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Customer;
import com.rburaksaritas.ordermanagementsystemapi.model.Product;
import com.rburaksaritas.ordermanagementsystemapi.model.Review;
import com.rburaksaritas.ordermanagementsystemapi.repository.CustomerRepository;
import com.rburaksaritas.ordermanagementsystemapi.repository.ProductRepository;
import com.rburaksaritas.ordermanagementsystemapi.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl(ProductRepository productRepository, CustomerRepository customerRepository, ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this. customerRepository = customerRepository;
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("review", "id", id));
        return modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        Review review = modelMapper.map(reviewDTO, Review.class);
        Review savedReview = reviewRepository.save(review);
        return modelMapper.map(savedReview, ReviewDTO.class);
    }

    @Override
    public ReviewDTO updateReview(Integer id, Integer updatedStar, String updatedDescription) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("review", "id", id));

        review.setStar(updatedStar);
        review.setDescription(updatedDescription);

        Review updatedReview = reviewRepository.save(review);
        return modelMapper.map(updatedReview, ReviewDTO.class);
    }

    @Override
    public void deleteReview(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("review", "id", id));
        reviewRepository.deleteById(id);
    }

    @Override
    public List<ReviewDTO> getReviewsOfProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        List<Review> reviews = reviewRepository.findByProduct(product);
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public double getAverageReviewOfProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        List<Review> reviews = reviewRepository.findByProduct(product);
        return reviews.stream()
                .mapToInt(Review::getStar)
                .average()
                .orElse(0.0);
    }

    @Override
    public List<ReviewDTO> getReviewsOfCustomer(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("customer", "id", customerId));
        List<Review> reviews = reviewRepository.findByCustomer(customer);
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }
}
