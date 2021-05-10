package com.example.electrowayfinal.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class JwtUtil {
    private String secret;
    private int jwtExpirationInMs;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("${jwt.expirationDateInMs}")
    public void setJwtExpirationInMs(int jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    // generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
//        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
//        if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//            claims.put("isAdmin", true);
//        }
//        if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
//            claims.put("isUser", true);
//        }
//        claims.put("isUser",true);
        return generateJWTToken(claims, userDetails.getUsername());
    }

    private String generateJWTToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public boolean validateToken(String authToken) {
        // Jwt token has not been tampered with
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
        return true;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String authToken) {
        List<SimpleGrantedAuthority> roles = null;
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
//        Boolean isAdmin = claims.get("isAdmin", Boolean.class);
        Boolean isUser = claims.get("isUser", Boolean.class);
        /*if (isAdmin != null && isAdmin == true) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }*/
        if (isUser != null && isUser) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return roles;
    }

}
