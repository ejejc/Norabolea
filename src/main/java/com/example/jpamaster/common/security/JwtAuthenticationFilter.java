package com.example.jpamaster.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private static final List<String> ACCESS_URL_LIST = Arrays.asList("/api/login", "/api/health");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        // 토큰이 없어도 securityContext에 존재해서 인증이 넘어간다 .. 이게 맞는걸까 ?
        // securityContext의 인증 객체를 null로 만들어준다. /login or / health 일 경우에는 token으로 판단하지 않고
        // UserDetailService에서 DB로 확인해서 인증객체에 넘어가게 하도록 설정
        String requestUrl = ((HttpServletRequest) request).getRequestURI();
        if (!ACCESS_URL_LIST.contains(requestUrl)) {
            if (!ObjectUtils.isEmpty(token) && jwtTokenProvider.valid(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }
        chain.doFilter(request, response);
    }
}
