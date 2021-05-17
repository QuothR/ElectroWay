package com.example.electrowayfinal.controllers;


import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ForgotPasswordController {

    private final JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    public ForgotPasswordController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "<h1>Forgot password form</h1>";
    }

    @PostMapping("/forgot_password")
    public String processForgotPasswordForm(HttpServletRequest request) throws MessagingException {
        String email = request.getParameter("email");
        String token = RandomString.make(45);

        log.info("token: " + token);

        userService.updateResetPasswordToken(token, email);

        String resetPasswordLink = "http://localhost:8090/reset_password?token=" + token;

        sendEmail(email, resetPasswordLink);

        return "<h1>Forgot password form</h1>";
    }

    @PostMapping("/reset_password")
    public String resetPassword(HttpServletRequest request) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.get(token);

        if (user == null)
            return "Invalid token";

        userService.updatePassword(user, password, token);

        return "password succesfully reset";
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email);
        helper.setSubject("ElectroWay Password Reset");
        String body = "<p>Hello, you've requested a password reset, please click the link below to change your password</p>" + resetPasswordLink;
        helper.setText(body, true);
        javaMailSender.send(message);
    }
}