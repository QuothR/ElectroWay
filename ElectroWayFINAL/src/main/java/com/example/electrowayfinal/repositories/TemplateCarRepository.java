package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.TemplateCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateCarRepository extends JpaRepository<TemplateCar, Long> {

    List<TemplateCar> findAll();

    <C extends TemplateCar> C save(C entity);

    void deleteById(long id);

    void delete(TemplateCar entity);
}
