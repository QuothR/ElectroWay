package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.emailVerification.VerificationToken;
import com.example.electrowayfinal.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {

    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
}
