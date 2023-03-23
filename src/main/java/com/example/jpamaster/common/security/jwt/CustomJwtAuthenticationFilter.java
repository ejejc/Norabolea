package com.example.jpamaster.common.security.jwt;

import static com.example.jpamaster.common.security.SecurityConfig.whiteList;

import com.example.jpamaster.common.security.oauth2.OAuth2AuthenticationProcessingException;
import com.nimbusds.jose.JOSEException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


@RequiredArgsConstructor
@Slf4j
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String requestSerializedToken = request.getHeader("Authorization");

        if (StringUtils.hasText(requestSerializedToken)) {
            Authentication authentication;

            try {
                authentication = jwtAuthenticationProvider.getAuthentication(requestSerializedToken.split(" ")[1]);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("SecurityContextHolder에 '{}' 인증 정보를 저장했습니다, uri: {}",
                        authentication.getName(),
                        request.getRequestURI());
                } else {
                    throw new OAuth2AuthenticationProcessingException("인증 정보를 찾을 수 없습니다.");
                }

            } catch (ParseException | JOSEException | OAuth2AuthenticationProcessingException e) {
                log.error("SecurityContextHolder에 인증 정보를 저장하지 못했습니다, uri: {}, message: {}",
                    request.getRequestURI(), e.getMessage(), e);
            }

        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Arrays.stream(whiteList)
            .map(path -> String.format("/api%s", path.replace("/**", "")))
            .anyMatch(path -> request.getRequestURI().startsWith(path));
    }

}
