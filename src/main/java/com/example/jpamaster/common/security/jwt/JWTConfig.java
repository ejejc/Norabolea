package com.example.jpamaster.common.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {

    @Value("{jwt.key}")
    private String jwtKey;

    @Bean
    public RSAKey rsaJwk() throws JOSEException {
        return new RSAKeyGenerator(2048)
            .keyID(jwtKey)
            .generate();
    }

    @Bean
    public JWSVerifier jwsVerifier() throws JOSEException {
        return new RSASSAVerifier(rsaJwk().toPublicJWK());
    }

    @Bean
    public JwtProvider jwtProvider() throws JOSEException {
        return new JwtProvider(rsaJwk());
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() throws JOSEException {
        return new JwtAuthenticationProvider(jwsVerifier());
    }

    @Bean
    public CustomJwtAuthenticationFilter customJwtAuthenticationFilter() throws JOSEException {
        return new CustomJwtAuthenticationFilter(jwtAuthenticationProvider());
    }
}
