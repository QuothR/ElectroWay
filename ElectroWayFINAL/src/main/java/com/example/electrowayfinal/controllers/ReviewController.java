package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.models.Review;
import com.example.electrowayfinal.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Qualifier("review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(path = "station/{id}")
    public Review createReview(@RequestBody Review review, @PathVariable("id") Long stationId, HttpServletRequest httpServletRequest) {
        reviewService.createReview(review, stationId, httpServletRequest);
        return reviewService.getReview(review.getId());
    }

    @GetMapping(path = "station/{id}/reviews")
    public List<Review> getStationReviews(@PathVariable("id") Long stationId) {
        return reviewService.getReviews(stationId);
    }
}
