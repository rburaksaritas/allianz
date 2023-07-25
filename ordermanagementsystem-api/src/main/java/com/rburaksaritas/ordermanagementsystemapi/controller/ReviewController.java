package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.ReviewDTO;
import com.rburaksaritas.ordermanagementsystemapi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    // TODO: Implement controller methods based on tests
    @GetMapping("/get")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return null;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Integer id) {
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO) {
        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable Integer id,
            @RequestParam Integer updatedStar,
            @RequestParam String updatedDescription
    ) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        return null;
    }
}
