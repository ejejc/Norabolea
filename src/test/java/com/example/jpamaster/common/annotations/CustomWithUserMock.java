package com.example.jpamaster.common.annotations;

import com.example.jpamaster.common.config.WithMockCustomUserSecurityContextFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface CustomWithUserMock {
    String registrationId() default "google";
    String name() default "안세형";
    String email() default "sag@gmail.com";
    String picture() default "";
    String[] authorities() default {"ROLE_USER"};
}
