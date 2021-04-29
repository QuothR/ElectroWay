package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository
        extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);
    <S extends Role> S save(S entity);
    @Override
    void delete(Role role);

}
