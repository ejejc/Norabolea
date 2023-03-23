package com.example.jpamaster.common.annotations;

import com.example.jpamaster.common.security.SecurityConfig;
import com.example.jpamaster.common.security.jwt.JWTConfig;
import com.example.jpamaster.common.security.oauth2.CustomOAuth2UserService;
import com.example.jpamaster.users.repository.UserRepository;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
    SecurityConfig.class,
    JWTConfig.class,
    CustomOAuth2UserService.class
})
@MockBean(UserRepository.class)
@WebMvcTest
public @interface SecurityWebConfigurationTest {

}
