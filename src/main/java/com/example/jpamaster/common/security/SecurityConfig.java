package com.example.jpamaster.common.security;

import com.example.jpamaster.common.security.jwt.CustomJwtAuthenticationFilter;
import com.example.jpamaster.common.security.jwt.JwtProvider;
import com.example.jpamaster.common.security.oauth2.CustomOAuth2UserService;
import com.example.jpamaster.common.security.oauth2.OAuth2LoginFailureHandler;
import com.example.jpamaster.common.security.oauth2.OAuth2LoginSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.ContentSecurityPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomOAuth2UserService oAuth2UserService;
    private final CustomJwtAuthenticationFilter customJwtAuthenticationFilter;


    public static final String[] whiteList = {
        "/h2-console/**",
        "/health/**",
        "/unhealth/**",
        "/oauth2/**",
        "/auth/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(authorize -> {
                authorize
                    .mvcMatchers(whiteList).permitAll()
                    .mvcMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
            })
            .oauth2Login()
            .userInfoEndpoint()
            .userService(oAuth2UserService)
            .and()
            .successHandler(authenticationSuccessHandler())
            .failureHandler(authenticationFailureHandler())
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .cors(c -> {
                CorsConfigurationSource source = request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.addAllowedOrigin("*");
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");
                    return config;
                };

                c.configurationSource(source);
            })
            .csrf()
            .ignoringAntMatchers("/h2-console/**").disable()
            .headers()
            .addHeaderWriter(
                new ContentSecurityPolicyHeaderWriter(
                    "frame-ancestors 'self'"
                )
            )
            .frameOptions().sameOrigin()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            })
            .and()
            .addFilterBefore(customJwtAuthenticationFilter, OAuth2LoginAuthenticationFilter.class)
            .addFilterBefore(customExceptionHandlingFilter(), CustomJwtAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new OAuth2LoginSuccessHandler(jwtProvider);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new OAuth2LoginFailureHandler();
    }

    @Bean
    public CustomExceptionHandlingFilter customExceptionHandlingFilter() {
        return new CustomExceptionHandlingFilter(new ObjectMapper());
    }
}
