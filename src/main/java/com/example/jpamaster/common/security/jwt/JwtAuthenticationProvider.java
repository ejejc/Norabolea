package com.example.jpamaster.common.security.jwt;

import static com.example.jpamaster.common.security.jwt.JwtProvider.JWT_CLAIM_ROLE_KEY;

import com.example.jpamaster.common.security.oauth2.CustomUserPrincipal;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {
    private final JWSVerifier jwsVerifier;

    public Authentication getAuthentication(String token) throws ParseException, JOSEException {
        SignedJWT parsedJwt = SignedJWT.parse(token);

        if (parsedJwt.verify(jwsVerifier)) {
            JWTClaimsSet jwtClaimsSet = parsedJwt.getJWTClaimsSet();
            jwtClaimsSet.getSubject();

            Set<? extends GrantedAuthority> authorities =
                Arrays.stream(jwtClaimsSet.getClaim(JWT_CLAIM_ROLE_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            CustomUserPrincipal principal = new CustomUserPrincipal(
                Long.parseLong(jwtClaimsSet.getAudience().get(0)),
                jwtClaimsSet.getIssuer(),
                jwtClaimsSet.getSubject(),
                jwtClaimsSet.getClaim(JwtProvider.JWT_CLAIM_EMAIL_KEY).toString(),
                jwtClaimsSet.getClaim(JwtProvider.JWT_CLAIM_PICTURE_KEY).toString(),
                authorities
            );


            return new UsernamePasswordAuthenticationToken(principal, "", authorities);
        }

        return null;
    }

}
