package com.example.jpamaster.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;


public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user = customUserDetailsService.loadUserByUsername(authentication.getName());
        if (!user.getPassword().equals(authentication.getCredentials())) {
            throw new InternalAuthenticationServiceException("유저정보가 일치하지 않습니다.");
        }
        UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user, authentication.getCredentials(), user.getAuthorities());
        authenticated.setDetails(authentication.getDetails());
        return authenticated;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("authentication = " + authentication);
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
