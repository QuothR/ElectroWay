package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.PlugType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlugTypeRepository extends JpaRepository<PlugType, Long> {
    List<PlugType> findAllByCarId(long car_id);

    @Override
    <C extends PlugType> C save(C entity);

    @Override
    PlugType getOne(Long carPlugTypeId);

    void deleteById(long carPlugTypeId);

    @Override
    void delete(PlugType entity);
}
