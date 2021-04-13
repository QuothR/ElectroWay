package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        System.out.println("logUser");
        return userRepository.findAll();
    }

    //TODO ???
    @Qualifier("getPasswordEncoder")
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUserAccount(User user){
        Optional<User> userOptional= userRepository.findUserByEmail(user.getEmail());
        if(userOptional.isPresent()){
            throw new IllegalStateException("email taken!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.println(user);
    }


    public void deleteUser(Long id){
        boolean exists = userRepository.existsById(id);
        if(!exists)
            throw new IllegalStateException("user with id " + id + " does NOT exist");
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long userId, String first_name, String last_name, String email){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("user with id " + userId + "does not exist"));
        if (first_name != null && first_name.length() > 0 && !Objects.equals(user.getFirst_name(), first_name))
            user.setFirst_name(first_name);
        if (last_name != null && last_name.length() > 0 && !Objects.equals(user.getLast_name(), last_name))
            user.setFirst_name(last_name);
        if  (email != null  && email.length() > 0 && !Objects.equals(user.getEmail(), email)){
            Optional<User> userOptional = userRepository.findUserByEmail(email);
            if (userOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            user.setEmail(email);
        }
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
    public boolean loggedIn( HttpServletRequest request, String name){
        if (request.getCookies() == null)
            return false;
        return Arrays.stream(request.getCookies()).anyMatch(cookie -> name.equals(cookie.getName()));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(s);

        user.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + s));

        return user.map(MyUserDetails::new).get();
    }


    public Optional<User> getOptionalUser(User user) {
        return userRepository.findUserByEmail(user.getEmail());
    }
}
