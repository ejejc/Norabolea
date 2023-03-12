package com.example.jpamaster.common.security;

import com.example.jpamaster.common.security.oauth2.CustomOAuth2User;
import com.example.jpamaster.common.security.oauth2.CustomOAuth2UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ContentSecurityPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> {

            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeRequests(auth -> {
                auth.antMatchers("/api/h2-console/**").permitAll()
                    .anyRequest().authenticated();
            })
            .oauth2Login()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
            .and()
            .successHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
                principal.getAttributes().forEach((key, value) -> {
                    System.out.println("key: " + key + ", value: " + value);
                });
            })
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
                    .ignoringAntMatchers("/api/h2-console/**").disable()
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
                .authenticationEntryPoint(((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                }))
                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                }))
            .and()
            .build();
    }

}
