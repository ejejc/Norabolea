package com.example.jpamaster.common.security;


import com.example.jpamaster.common.ApiResponse;
import com.example.jpamaster.common.security.oauth2.OAuth2AuthenticationProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class CustomExceptionHandlingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (OAuth2AuthenticationProcessingException e) {
            response.sendError(e.getHttpStatusCode().getStatus());
            response.getWriter().write(
                objectMapper.writeValueAsString(
                    ApiResponse.createError(
                        e.getHttpStatusCode(),
                        e.getMessage()
                    )
                )
            );
        }
    }
}
