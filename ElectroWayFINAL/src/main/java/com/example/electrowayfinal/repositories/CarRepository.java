package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByUserId(long user_id);

    List<Car> findAll();

    <C extends Car> C save(C entity);
}
