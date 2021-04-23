package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailAddress(String emailAddress);
    Optional<User> findUserByPasswordResetToken(String passwordResetToken);
    Optional<User> findUserByUsername(String username);
}
