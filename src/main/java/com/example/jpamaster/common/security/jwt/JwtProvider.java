package com.example.jpamaster.common.security.jwt;

import com.example.jpamaster.common.security.oauth2.CustomUserPrincipal;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    public static final int JWT_TOKEN_TIME = 1 * 60 * 1000;
    public static final String JWT_CLAIM_ROLE_KEY = "role";
    public static final String JWT_CLAIM_EMAIL_KEY = "email";
    public static final String JWT_CLAIM_PICTURE_KEY = "picture";
    private final RSAKey rsaJWK;
    private JWSSigner signer;

    @PostConstruct
    public void setup() throws JOSEException {
        signer = new RSASSASigner(rsaJWK);
    }

    public String generateSignedToken(OAuth2User oAuth2User) throws JOSEException {
        CustomUserPrincipal principal = (CustomUserPrincipal) oAuth2User;
        Date date = new Date();

        String role = oAuth2User.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject(principal.getUsername())
            .issuer(principal.getRegistrationId())
            .audience(String.valueOf(principal.getUserSeq()))
            .jwtID(UUID.randomUUID().toString())
            .issueTime(date)
            .expirationTime(new Date(date.getTime() + JWT_TOKEN_TIME))
            .claim(JWT_CLAIM_ROLE_KEY, role)
            .claim(JWT_CLAIM_EMAIL_KEY, principal.getEmail())
            .claim(JWT_CLAIM_PICTURE_KEY, principal.getPicture())
            .build();

        SignedJWT signedJWT = new SignedJWT(
            new JWSHeader.Builder(JWSAlgorithm.RS512).keyID(rsaJWK.getKeyID()).build(),
            claimsSet);

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }
}
