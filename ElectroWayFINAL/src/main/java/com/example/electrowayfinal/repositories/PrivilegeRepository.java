package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository
        extends JpaRepository<Privilege,Long> {

    Privilege findByName(String name);
    <S extends Privilege> S save(S entity);
    @Override
    void delete(Privilege privilege);
}
