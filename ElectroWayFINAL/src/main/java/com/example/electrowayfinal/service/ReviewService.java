package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.exceptions.WrongPrivilegesException;
import com.example.electrowayfinal.exceptions.WrongUserInServiceException;
import com.example.electrowayfinal.models.*;
import com.example.electrowayfinal.repositories.ReviewRepository;
import com.example.electrowayfinal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final StationService stationService;
    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserService userService, StationService stationService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.stationService = stationService;
    }

    public void createReview(Review review, Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);
        review.setUser(user);

        Station station = stationService.getCurrentStation(stationId, httpServletRequest, httpServletResponse);
        review.setStation(station);

        reviewRepository.save(review);
    }

    public Review getReview(Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws WrongUserInServiceException, WrongAccessException, UserNotFoundException {
        checkUserAndStation(httpServletRequest, httpServletResponse);

        return reviewRepository.getOne(stationId);
    }

    public Review getReview(Long reviewId, Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws WrongUserInServiceException, WrongAccessException, UserNotFoundException {
        checkUserAndStation(httpServletRequest, httpServletResponse);

        Optional<Review> optionalReview = reviewRepository.findReviewByStationIdAndId(stationId, reviewId);
        if (optionalReview.isEmpty()) {
            throw new WrongAccessException("Wrong review in station!");
        }
        return optionalReview.get();
    }

    public List<Review> getReviews(Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException, WrongAccessException {
        checkUserAndStation(httpServletRequest, httpServletResponse);

        return reviewRepository.findAllByStationId(stationId);
    }

    public Review updateReview(Review review, Long reviewId, Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        Review reviewToUpdate = getReview(reviewId, stationId, httpServletRequest, httpServletResponse);

        reviewToUpdate.setTextReview(review.getTextReview());
        reviewToUpdate.setRating(review.getRating());

        reviewRepository.save(reviewToUpdate);
        return reviewToUpdate;
    }

    public void deleteReview(Long reviewId, Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException, WrongAccessException {
        checkUserAndStation(httpServletRequest, httpServletResponse);

        Optional<Review> optionalReview = reviewRepository.findReviewByStationIdAndId(stationId, reviewId);
        if (optionalReview.isEmpty()) {
            throw new WrongAccessException("Wrong review in station!");
        }
        reviewRepository.deleteById(reviewId);
    }

    private void checkUserAndStation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        if (!user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_OWNER")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new WrongPrivilegesException("Can't access station without being a station owner!");
        }
    }
}
