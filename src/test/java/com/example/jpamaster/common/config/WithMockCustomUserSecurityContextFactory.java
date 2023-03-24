package com.example.jpamaster.common.config;

import com.example.jpamaster.common.annotations.CustomWithUserMock;
import com.example.jpamaster.common.security.oauth2.CustomUserPrincipal;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<CustomWithUserMock> {

    @Override
    public SecurityContext createSecurityContext(CustomWithUserMock annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        CustomUserPrincipal principal =
            new CustomUserPrincipal(1L,
                annotation.registrationId(), annotation.name(),
                annotation.email(), annotation.picture(),
                Arrays.stream(annotation.authorities())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet())
            );

        Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
