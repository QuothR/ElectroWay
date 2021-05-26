package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.models.AuthenticationRequest;
import com.example.electrowayfinal.models.AuthenticationResponse;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.service.UserService;
import com.example.electrowayfinal.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "https://electrowayweb.herokuapp.com")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        log.info("in login, request body is: " + authenticationRequest.getEmail() + "\npassword: " + authenticationRequest.getPassword());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        Optional<User> user  = userService.findUserByEmailAddress(authenticationRequest.getEmail());
        if(user.isEmpty())
            throw new UsernameNotFoundException(authenticationRequest.getEmail());



        final String token = jwtTokenUtil.generateToken(user.get());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
