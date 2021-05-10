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
public class EmailService {
    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(VerificationTokenService verificationTokenService, TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.verificationTokenService = verificationTokenService;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendHtmlMail(User user) throws MessagingException {
        VerificationToken verificationToken = verificationTokenService.findByUser(user);


        if (verificationToken != null) {
            String token = verificationToken.getToken();
            Context context = new Context();
            context.setVariable("title", "Verify your email adress");
            context.setVariable("link", "http://localhost:8090/activation?token=" + token);

            String body = templateEngine.process("verification", context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmailAddress());
            helper.setSubject("email adress verification");
            helper.setText(body, true);
            javaMailSender.send(message);
        }

    }
}
