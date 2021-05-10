package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.ChargingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChargingPointRepository extends JpaRepository<ChargingPoint, Long> {

    List<ChargingPoint> findAll();

    List<ChargingPoint> findChargingPointsByStation_Id(long station_id);

    Optional<ChargingPoint> getChargingPointById(long id);

    void deleteById(Long id);

    <C extends ChargingPoint> C save(C entity);

    void delete(ChargingPoint chargingPoint);
}
