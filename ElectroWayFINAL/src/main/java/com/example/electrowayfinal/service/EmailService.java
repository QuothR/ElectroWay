package com.example.electrowayfinal.service;

import com.example.electrowayfinal.emailVerification.VerificationToken;
import com.example.electrowayfinal.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService{
    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(VerificationTokenService verificationTokenService, JavaMailSender javaMailSender) {
        this.verificationTokenService = verificationTokenService;
        this.javaMailSender = javaMailSender;
    }

    public void sendHtmlMail(User user) throws MessagingException {
        VerificationToken verificationToken = verificationTokenService.findByUser(user);

        String token = verificationToken.getToken();
        String body = "http://localhost:8090/activation?token=" + token;

        if (verificationToken != null) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmailAddress());
            mailMessage.setFrom("stufftesting469@gmail.com");
            mailMessage.setSubject("Test Spring Email");
            mailMessage.setText(body);
            javaMailSender.send(mailMessage);
        }



//            MimeMessage message = javaMailSender.createMimeMessage();
//
//            MimeMessageHelper helper = new MimeMessageHelper(message,true);
//            helper.setTo(user.getEmailAddress());
//            helper.setSubject("email adress verification");
//            helper.setText(body);
//            javaMailSender.send(message)
    }
}
