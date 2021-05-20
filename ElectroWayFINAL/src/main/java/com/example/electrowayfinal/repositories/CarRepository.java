package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByUserId(long user_id);

    @Override
    List<Car> findAll();

    @Override
    <C extends Car> C save(C entity);

    @Override
    Car getOne(Long carId);

    void deleteById(long id);

    @Override
    void delete(Car entity);
}
