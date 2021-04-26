package com.example.electrowayfinal.service;

import com.example.electrowayfinal.models.Review;
import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.ReviewRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

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

    public ReviewService(ReviewRepository reviewRepository, UserService userService, StationService stationService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.stationService = stationService;
    }

    public void createReview(Review review, Long stationId, HttpServletRequest httpServletRequest) throws Exception {

        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty())
            throw new Exception("wrong user in review service");

        review.setUser(optionalUser.get());

        Optional<Station> optionalStation = stationService.getCurrentStation(stationId);
        if (optionalStation.isEmpty())
            throw new Exception("wrong station in review service");

        review.setStation(optionalStation.get());

        reviewRepository.save(review);
    }

    public Review getReview(Long stationId) {
        return reviewRepository.getOne(stationId);
    }

    public List<Review> getReviews(Long stationId) {
        return reviewRepository.findAllByStationId(stationId);
    }
}
