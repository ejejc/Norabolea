package com.example.jpamaster.common.security.jwt;

import static com.example.jpamaster.common.security.SecurityConfig.whiteList;

import com.example.jpamaster.common.enums.HttpStatusCode;
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
import org.springframework.web.filter.OncePerRequestFilter;


@RequiredArgsConstructor
@Slf4j
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String requestSerializedToken = request.getHeader(AUTHORIZATION);

        if (requestSerializedToken == null || !requestSerializedToken.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication;
        String token = requestSerializedToken.split(" ")[1].trim();

        try {
            authentication = jwtAuthenticationProvider.getAuthentication(token);
        } catch (ParseException | JOSEException e) {
            log.error("principal 정보 변환에 실패했습니다. requestUri : {}, token : {}, message : {}",
                request.getRequestURI(), token, e.getMessage(), e);
            throw new OAuth2AuthenticationProcessingException("유효하지 않은 토큰입니다.", HttpStatusCode.BAD_REQUEST);
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("SecurityContextHolder 에 '{}' 인증 정보를 저장했습니다, uri: {}",
                authentication.getPrincipal(),
                request.getRequestURI());
        } else {
            log.error("[Token Expired] JWT expired : {}", token);
            throw new OAuth2AuthenticationProcessingException("토큰이 만료되었습니다.", HttpStatusCode.UNAUTHORIZED);
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
