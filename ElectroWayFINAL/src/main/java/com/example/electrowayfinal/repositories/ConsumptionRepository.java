package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {

    List<Consumption> findAllByCarId(long car_id);

    @Override
    List<Consumption> findAll();

    Optional<Consumption> findConsumptionByCarIdAndId(long car_id, long id);

    @Override
    <C extends Consumption> C save(C entity);

    @Override
    Consumption getOne(Long consumptionId);

    void deleteById(long id);

    @Override
    void delete(Consumption entity);
}
