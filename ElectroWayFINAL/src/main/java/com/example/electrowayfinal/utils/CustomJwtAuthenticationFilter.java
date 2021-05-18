package com.example.electrowayfinal.utils;

import com.example.electrowayfinal.models.User;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@PropertySource(value = {"classpath:application.properties"})
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtTokenUtil;

    //when this call??
    //once per request lol
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
//        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
//        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
//        response.addHeader("Access-Control-Allow-Credentials", "true");
//        response.addIntHeader("Access-Control-Max-Age", 50);

        // JWT Token is in the form "Bearer token". Remove Bearer word and
        // get  only the Token
        String jwtToken = extractJwtFromRequest(request);
        if (StringUtils.hasText(jwtToken) && jwtTokenUtil.validateToken(jwtToken)) {
            UserDetails userDetails = new User(jwtTokenUtil.getUsernameFromToken(jwtToken), "");  //need to add one more param getRolesFromToken

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            // After setting the Authentication in the context, we specify
            // that the current user is authenticated. So it passes the
            // Spring Security Configurations successfully.
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else {
            //log.error("Cannot set the Security Context");
            log.info("Security Context properly set!");
        }
        chain.doFilter(request, response);
    }

    public String extractJwtFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
