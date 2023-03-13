package com.example.jpamaster.common.security.oauth2;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;


@ToString
public class CustomUserPrincipal implements OAuth2User, UserDetails {

    private final Long userSeq;
    private final String registrationId;
    private final String username;
    private final String email;
    private final String picture;
    private  Map<String, Object> attributes;
    private final Set<? extends GrantedAuthority> authorities;

    public CustomUserPrincipal(Long userSeq, String registrationId, String name, String email,
        String picture, Set<? extends GrantedAuthority> authorities) {
        this(userSeq, registrationId, name, email, picture, Collections.emptyMap(), authorities);
    }

    private CustomUserPrincipal(Long userSeq, String registrationId, String name, String email, String picture,
        Map<String, Object> attributes, Set<? extends GrantedAuthority> authorities) {
        this.userSeq = userSeq;
        this.registrationId = registrationId;
        this.username = name;
        this.email = email;
        this.picture = picture;
        this.attributes = attributes;
        this.authorities = authorities;
    }

    public static CustomUserPrincipal create(Long userSeq, String registrationId, String name, String email, String picture,
        Map<String, Object> attributes, Set<? extends GrantedAuthority> authorities) {
        return new CustomUserPrincipal(
            userSeq,
            registrationId,
            name,
            email,
            picture,
            attributes,
            authorities);
    }

    public Long getUserSeq() {
        return userSeq;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>(this.attributes);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableSet(this.authorities);
    }

    @Override
    public String getName() {
        return this.getUsername();
    }
}
