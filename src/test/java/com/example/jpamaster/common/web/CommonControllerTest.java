package com.example.jpamaster.common.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.jpamaster.common.annotations.CustomWithUserMock;
import com.example.jpamaster.common.security.SecurityConfig;
import com.example.jpamaster.common.security.jwt.JWTConfig;
import com.example.jpamaster.common.security.oauth2.CustomOAuth2UserService;
import com.example.jpamaster.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
@ContextConfiguration(
    classes = {
        SecurityConfig.class,
        JWTConfig.class,
        CustomOAuth2UserService.class,
        CommonController.class
    }
)
@MockBean(classes = {UserRepository.class})
public class CommonControllerTest {


    @Autowired
    private MockMvc mvc;


    @DisplayName("스프링 시큐리티 구성 테스트 - mvcMatchers 에 permitAll 로 등록된 경로에 대해 200 ok 발생하는 요청")
    @Test
    void given_whenAllowedMatchers_thenResponse200Ok() throws Exception {
        // when
        mvc.perform(
                MockMvcRequestBuilders.get("/health"))
            .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @DisplayName("스프링 시큐리티 구성 테스트 - mvcMatchers 에 authenticated 로 등록된 경로에 대해 401 발생하는 요청")
    @Test
    void given_whenNotAllowedMatchers_thenResponse401() throws Exception {
        // when
        mvc.perform(
                MockMvcRequestBuilders.get("/airport"))
            .andExpect(status().isUnauthorized());
    }

    @CustomWithUserMock
    @DisplayName("스프링 시큐리티 구성 테스트 - mvcMatchers 에 hasRole('ADMIN') 로 등록된 경로에 대해 403 발생하는 요청")
    @Test
    void given_whenNotAllowedMatcherWithRoles_thenResponse403() throws Exception {
        // when && then
        mvc.perform(
                MockMvcRequestBuilders.get("/admin/airline"))
            .andExpect(status().isForbidden());
    }

}
