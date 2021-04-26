package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByStationId(long station_id);

    List<Review> findAll();

    <R extends Review> R save(R entity);
}
