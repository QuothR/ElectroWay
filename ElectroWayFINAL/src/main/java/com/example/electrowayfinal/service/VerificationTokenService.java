package com.example.electrowayfinal.service;

import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.models.VerificationToken;
import com.example.electrowayfinal.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Transactional
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Transactional
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }

    @Transactional
    public void save(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        // set expiry date to 5m
        verificationToken.setExpiryDate(calculateExpiryDate(300));
        verificationTokenRepository.save(verificationToken);
    }

    //calculate expiry date
    private Timestamp calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }
}
