package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.Review;
import com.example.electrowayfinal.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Qualifier("review")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(path = "station/{stationId}")
    @ResponseStatus(HttpStatus.OK)
    public Review createReview(@RequestBody Review review, @PathVariable("stationId") Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        reviewService.createReview(review, stationId, httpServletRequest, httpServletResponse);
        return reviewService.getReview(review.getId(), httpServletRequest, httpServletResponse);
    }

    @GetMapping(path = "station/{stationId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    public List<Review> getStationReviews(@PathVariable("stationId") Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return reviewService.getReviews(stationId, httpServletRequest, httpServletResponse);
    }

    @GetMapping(path = "station/{stationId}/review/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public Review getStationReview(@PathVariable("stationId") Long stationId, @PathVariable("reviewId") Long reviewId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return reviewService.getReview(stationId, reviewId, httpServletRequest, httpServletResponse);
    }

    @DeleteMapping(path = "station/{stationId}/review/delete/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStationReview(@PathVariable("stationId") Long stationId, @PathVariable("reviewId") Long reviewId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        reviewService.deleteReview(stationId, reviewId, httpServletRequest, httpServletResponse);
    }
}
