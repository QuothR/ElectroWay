package com.example.electrowayfinal.repositories;


import com.example.electrowayfinal.models.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository
        extends JpaRepository<Station,Long> {
    Optional<Station> findStationById(Long id);
    void deleteById(Long id);
    List<Station> findAll();
    Station getOne(Long aLong);
    void delete(Station entity);
    <S extends Station> S save(S entity);
}
