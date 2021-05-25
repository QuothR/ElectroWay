package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.Review;
import com.example.electrowayfinal.models.TemplateCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByStationId(long station_id);

    @Override
    List<Review> findAll();

    Optional<Review> findReviewByStationIdAndId(Long stationId, Long reviewId);

    Review findReviewByStationId(long station_id);

    @Override
    <R extends Review> R save(R entity);

    void deleteById(long id);

    @Override
    void delete(Review entity);
}
