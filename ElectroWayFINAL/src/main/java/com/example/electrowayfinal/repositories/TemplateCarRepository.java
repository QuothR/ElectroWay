package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.TemplateCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateCarRepository extends JpaRepository<TemplateCar, Long> {

    @Override
    List<TemplateCar> findAll();

    @Override
    <C extends TemplateCar> C save(C entity);

    @Override
    TemplateCar getOne(Long templateCarId);

    void deleteById(long id);

    @Override
    void delete(TemplateCar entity);
}
