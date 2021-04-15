package com.example.electrowayfinal.service;

import com.example.electrowayfinal.models.PasswordResetToken;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository, EmailService emailService){
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Transactional
    public PasswordResetToken findByToken(String token){
        // : )
        return  passwordResetTokenRepository.findByToken(token);
    }

    @Transactional
    public PasswordResetToken findByUser(User user){
        return passwordResetTokenRepository.findByUser(user);
    }

    @Transactional
    public void save(User user, String token){
        PasswordResetToken passwordResetToken = new PasswordResetToken(token,user);
        passwordResetTokenRepository.save(passwordResetToken);
    }
}
