package com.example.jpamaster.common.security;

import com.example.jpamaster.common.security.jwt.CustomJwtAuthenticationFilter;
import com.example.jpamaster.common.security.jwt.JwtProvider;
import com.example.jpamaster.common.security.oauth2.CustomOAuth2UserService;
import com.example.jpamaster.common.security.oauth2.OAuth2LoginFailureHandler;
import com.example.jpamaster.common.security.oauth2.OAuth2LoginSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    public static final String[] whiteList = {
        "/h2-console/**",
        "/health/**",
        "/unhealth/**",
        "/oauth2/**",
        "/auth/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .mvcMatchers("/user/create").permitAll()
                .anyRequest().authenticated();



        /*http
                .addFilterBefore(new CustomFilter(), UsernamePasswordAuthenticationFilter.class);*/

        http.formLogin()
                .usernameParameter("userId")
                .passwordParameter("password")
                .failureHandler(
                        new AuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                System.out.println("exception = " + exception.getMessage());
                            }
                        }
                )
                .successHandler((req, res, auth) -> {

                    /*CustomUserDetails principal = (CustomUserDetails) auth.getPrincipal();

                    String token = jwt.encoding(principal.getUser().getBirth(), principal.getUser().getPhoneNo());
                    res.addCookie(new Cookie("token", token));*/

                });


        return http.build();
//
//        return http
//            .authorizeHttpRequests(authorize -> {
//                authorize
//                    .mvcMatchers(whiteList).permitAll()
//                    .mvcMatchers("/admin/**").hasRole("ADMIN")
//                    .anyRequest().authenticated();
//            })
//            .oauth2Login()
//            .userInfoEndpoint()
//            .userService(oAuth2UserService)
//            .and()
//            .successHandler(authenticationSuccessHandler())
//            .failureHandler(authenticationFailureHandler())
//            .and()
//            .httpBasic().disable()
//            .formLogin().disable()
//            .cors(c -> {
//                CorsConfigurationSource source = request -> {
//                    CorsConfiguration config = new CorsConfiguration();
//                    config.setAllowCredentials(true);
//                    config.addAllowedOrigin("*");
//                    config.addAllowedHeader("*");
//                    config.addAllowedMethod("*");
//                    return config;
//                };
//
//                c.configurationSource(source);
//            })
//            .csrf()
//            .ignoringAntMatchers("/h2-console/**").disable()
//            .headers()
//            .addHeaderWriter(
//                new ContentSecurityPolicyHeaderWriter(
//                    "frame-ancestors 'self'"
//                )
//            )
//            .frameOptions().sameOrigin()
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .exceptionHandling()
//            .authenticationEntryPoint((request, response, authException) -> {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            })
//            .accessDeniedHandler((request, response, accessDeniedException) -> {
//                response.sendError(HttpServletResponse.SC_FORBIDDEN);
//            })
//            .and()
//            .addFilterBefore(customJwtAuthenticationFilter, OAuth2LoginAuthenticationFilter.class)
//            .addFilterBefore(customExceptionHandlingFilter(), CustomJwtAuthenticationFilter.class)
//            .build();
    }

   /* @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new OAuth2LoginSuccessHandler(jwtProvider);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new OAuth2LoginFailureHandler();
    }

    @Bean
    public CustomExceptionHandlingFilter customExceptionHandlingFilter() {
        return new CustomExceptionHandlingFilter(new ObjectMapper());*/
    }

    /*@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }*/

