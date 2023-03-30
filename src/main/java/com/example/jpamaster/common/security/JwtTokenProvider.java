package com.example.jpamaster.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private long tokenValid;
    public String createToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());

        Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date()).compact();

        return null;
    }
}
