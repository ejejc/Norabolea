package com.example.jpamaster.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final String SECRET_KEY = "jpamaster";
    private static final String USER_SEQ_KEY = "userSeq";
    private static final String BIRTH_KEY = "birth";
    private static final String ROLE_KEY = "role";

    private JwtClaims jwtClaims;
    private final CustomUserDetailsService userDetailsService;
    private long tokenValidTime = 30 * 60 * 1000L; // 토큰 유효시간 10분
    public String createToken(CustomUserDetails userDetails) {
        Map<String, Object> map = new HashMap<>();
        map.put(USER_SEQ_KEY, userDetails.getUser().getUserSeq());
        map.put(BIRTH_KEY, userDetails.getUser().getBirth());
        map.put(ROLE_KEY, userDetails.getRoles());
        Claims claims = Jwts.claims(map);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(new Date().getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean valid(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        return !claims.getBody().getIssuedAt().before(new Date());
    }

    public Authentication getAuthentication(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        UserDetails userDetails = userDetailsService.loadUserByUserSeq(Long.valueOf(String.valueOf(claims.getBody().get(USER_SEQ_KEY))));
        if (ObjectUtils.isEmpty(userDetails)) throw new InvalidParameterException("유효하지 않는 토큰입니다.");
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
