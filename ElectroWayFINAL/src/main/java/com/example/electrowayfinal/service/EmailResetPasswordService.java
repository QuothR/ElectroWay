package com.example.electrowayfinal.service;

import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailResetPasswordService {
    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailResetPasswordService(VerificationTokenService verificationTokenService, TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.verificationTokenService = verificationTokenService;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendHtmlMail(User user) throws MessagingException {
        VerificationToken verificationToken = verificationTokenService.findByUser(user);


        if (verificationToken != null) {
            String token = user.getPasswordResetToken();
            Context context = new Context();
            context.setVariable("title", "Click here to reset your password");
            context.setVariable("link", "http://localhost:8090/reset_password?token=" + token);

            String body = templateEngine.process("verification", context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmailAddress());
            helper.setSubject("reset password");
            helper.setText(body, true);
            javaMailSender.send(message);
        }

    }
}
