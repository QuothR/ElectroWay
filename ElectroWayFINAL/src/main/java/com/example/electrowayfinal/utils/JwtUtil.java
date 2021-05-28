package com.example.electrowayfinal.utils;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.exceptions.WrongPrivilegesException;
import com.example.electrowayfinal.models.Role;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.service.UserService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

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
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
//        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        List<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toCollection(ArrayList::new));

        if (roleNames.contains("ROLE_ADMIN")) {
            claims.put("isAdmin", true);
        }
        if (roleNames.contains("ROLE_DRIVER")) {
            claims.put("isUser", true);
        }
        if (roleNames.contains("ROLE_OWNER")) {
            claims.put("isOwner", true);
        }

        return generateJWTToken(claims, user.getUsername());
    }

    private String generateJWTToken(Map<String, Object> claims, String subject) {
        System.out.println(Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)).signWith(SignatureAlgorithm.HS512, secret).compact());

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

    public static User getUserFromToken(UserService userService, String secret, HttpServletRequest httpServletRequest) throws UserNotFoundException {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        bearerToken = bearerToken.substring(6);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(bearerToken).getBody();
        String username = claims.getSubject();

        Optional<User> optionalUser = userService.getOptionalUserByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        return optionalUser.get();
    }

    public static User checkUserIsDriver(UserService userService, String secret, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = getUserFromToken(userService, secret, httpServletRequest);

        if (!user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_DRIVER")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new WrongPrivilegesException("Can't access car without being a car owner!");
        }
        return user;
    }

}
