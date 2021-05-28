package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.ChargingPlug;
import com.example.electrowayfinal.models.ChargingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChargingPlugRepository extends JpaRepository<ChargingPlug, Long> {

    List<ChargingPlug> findAll();

    List<ChargingPlug> findChargingPlugsByChargingPoint(ChargingPoint chargingPoint);

    void deleteById(Long id);

    void delete(ChargingPlug chargingPlug);

    <C extends ChargingPlug> C save(C entity);

    List<ChargingPlug> findChargingPlugsByConnectorType(String connectorType);

    Optional<ChargingPlug> findChargingPlugById(Long id);

    List<ChargingPlug> findChargingPlugsByChargingPointId(long chargingPoint_id);

}
