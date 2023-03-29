package com.example.jpamaster.common.security.oauth2;

import com.example.jpamaster.common.security.jwt.JwtProvider;
import com.nimbusds.jose.JOSEException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;



public class OAuth2LoginSuccessHandler  {
/*
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;

        CustomUserPrincipal principal = (CustomUserPrincipal) authenticationToken.getPrincipal();
        String serializedToken = null;
        try {
            serializedToken = jwtProvider.generateSignedToken(principal);
        } catch (JOSEException e) {
            log.error("Error while generating signed token", e);
        } finally {
            response.setHeader("Authorization", serializedToken);
            response.sendRedirect("/api/health");
        }
    }*/
}
