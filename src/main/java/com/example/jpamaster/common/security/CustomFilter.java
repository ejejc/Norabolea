package com.example.jpamaster.common.security;

import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

public class CustomFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //        System.out.println("request = " + request);
        /*long userSeq = 1;
        userDetails = customuserdetailsService.loadUserByUsername(userSeq);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userdetilas, userdetails.getpassword, userdtails.getAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);*/
    }
}
