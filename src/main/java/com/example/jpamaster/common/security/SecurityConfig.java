package com.example.jpamaster.common.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
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
                .mvcMatchers(HttpMethod.POST,"/accommodation").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/answer").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/features").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/facility").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/coupon").hasRole("ADMIN")
                .mvcMatchers("/review/best/add").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin()
                .usernameParameter("userId")
                .passwordParameter("password")
                .failureHandler(
                        new AuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                                    throws IOException, ServletException {
                                // TODO: response.sendError가 해당 response code에 맞는 에러 페이지를 보여준다고 했는데 왜 에러페이지가 안보일까 ?
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
                            }
                        }
                )
                .successHandler((req, res, auth) -> {
                    /**
                     * 인증 전 : principal - 로그인 시도 아이디, credentials - 로그인 시도 비밀번호
                     * 인증 후 :
                     * principal
                     *  - 인증이 완료된 사용자 객체(UserDetails의 구현체)
                     * credentials
                     *  - 인증 완료 후 유출 가능성을 줄이기 위해 삭제
                     * authorities
                     *  - 인증된 사용자가 가지는 권한 목록
                     * authenticated
                     *  - 인증 여부 (true)
                     */
                    String token = jwtTokenProvider.createToken((CustomUserDetails)auth.getPrincipal());
                    System.out.println("token = " + token);
                    res.addCookie(new Cookie("jpaMasterToken", token)); // header에 넣어줘도 소용 없는 것 같은데 .. ?
                    // 단순히 /health 엔드포인트로 redirect 하지 않고 token을 뿌려주는건 어떨까 ?
                    res.sendRedirect("/api/health");
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
        return new CustomExceptionHandlingFilter(new ObjectMapper());
    }*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

}

