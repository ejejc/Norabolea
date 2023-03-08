package com.example.jpamaster.common.security.oauth2;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;


public class CustomOAuth2User implements OAuth2User {
    private final Long userSeq;

    private final OAuth2User oAuth2User;

    public CustomOAuth2User(Long userSeq, OAuth2User oAuth2User) {
        this.userSeq = userSeq;
        this.oAuth2User = oAuth2User;
    }

    public Long getUserSeq() {
        return userSeq;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }
}
