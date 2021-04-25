package com.example.electrowayfinal.service;

import com.example.electrowayfinal.repositories.UserRepository;
import com.example.electrowayfinal.user.MyUserDetails;
import com.example.electrowayfinal.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;


@Qualifier("userService")
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;
    private String secret;
    @Value("electroway")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public UserService(UserRepository userRepository, VerificationTokenService verificationTokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
    }

    public List<User> getUsers() {
        System.out.println("logUser");
        return userRepository.findAll();
    }


    //TODO ???
    @Qualifier("getPasswordEncoder")
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUserAccount(User user) {
        Optional<User> userOptional = userRepository.findUserByEmailAddress(user.getEmailAddress());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("email taken!");
        }

        // Se comenteaza pentru ca: Validarea parolei se face pe hashPassword
        // Dupa rezolvarea problemei, se decomenteaza

        String encryptedPassword;

        encryptedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);
        user.setEnabled(false);

        Optional<User> saved = Optional.of(user);
        saved.ifPresent(u -> {
            try {
                String token = UUID.randomUUID().toString();
                verificationTokenService.save(user, token);

                try {
                    emailService.sendHtmlMail(u);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        userRepository.save(user);
        System.out.println(user);

        //saved.get();

    }
    public Optional<User> getCurrentUser(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = getOptionalUserByUsername(username);
        return optionalUser;

    }

    public void deleteUser(Long id) {
        boolean exists = userRepository.existsById(id);
        if (!exists)
            throw new IllegalStateException("user with id " + id + " does NOT exist");
        userRepository.deleteById(id);
    }

    public void updateUser(User modifiedUser,HttpServletRequest httpServletRequest) throws Exception {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = getOptionalUserByUsername(username);
        if (optionalUser.isEmpty())
            throw new Exception("wrong user???!!!??");
        userRepository.save(modifiedUser);
    }

    //TO DELETE???
    @Transactional
    public void updateUser(Long userId, String firstName, String lastName, String emailAddress) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("user with id " + userId + "does not exist"));
        if (firstName != null && firstName.length() > 0 && !Objects.equals(user.getFirstName(), firstName))
            user.setFirstName(firstName);
        if (lastName != null && lastName.length() > 0 && !Objects.equals(user.getLastName(), lastName))
            user.setFirstName(lastName);
        if (emailAddress != null && emailAddress.length() > 0 && !Objects.equals(user.getEmailAddress(), emailAddress)) {
            Optional<User> userOptional = userRepository.findUserByEmailAddress(emailAddress);
            if (userOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            user.setEmailAddress(emailAddress);
        }
    }

    @Transactional
    public void enableUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("user with id " + userId + "does not exist"));
        user.setEnabled(true);
    }
    /*
        public void login(HttpServletRequest request, HttpServletResponse response, String email, String password){
            Optional<User> userOptional = userRepository.findUserByEmail(email);
            if(userOptional.isEmpty())
                throw new IllegalStateException("The email " + email + " does not appear in the database");
            if (!password.equals(userOptional.get().getPassword()))
                throw new IllegalStateException("The password  is invalid");
            if (loggedIn(request,"logged-in"))
                throw new IllegalStateException("the user " + email +" is already logged in");

            Cookie cookie = new Cookie("logged-in",email);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setPath("/users/login");
            response.addCookie(cookie);
        }
    */


    // :))))) Aici face load user by email address -> Cringe
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmailAddress(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

        return user.map(MyUserDetails::new).get();
    }

    public Optional<User> getOptionalUser(String email) {
        return userRepository.findUserByEmailAddress(email);
    }

    public Optional<User> getOptionalUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    public void updateResetPasswordToken(String token, String email) throws Exception {
        Optional<User> user = userRepository.findUserByEmailAddress(email);

        if (user.isPresent()) {
            user.get().setPasswordResetToken(token);
            userRepository.save(user.get());
        } else {
            throw new Exception("cringe");
        }
    }

    public User get(String passwordResetToken) {
        return userRepository.findUserByPasswordResetToken(passwordResetToken).isPresent()
                ? userRepository.findUserByPasswordResetToken(passwordResetToken).get()
                : null;

    }

    public void updatePassword(User user, String newPassword, String passwordResetToken) {
        //TODO :> OwO :^)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        user.setPasswordResetToken(passwordResetToken);

        userRepository.save(user);
    }


}
