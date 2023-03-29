package com.example.jpamaster.common.security;

import com.example.jpamaster.users.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    /*private com.example.jpamaster.users.domain.User user;
    private List<CustomRole> roles;
    private String token;

    public CustomUserDetails(User user, List<CUstomROle> roles) {
        *//*this.user = user;
        this.roles = roles*//*
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return List.of();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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

    /*public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }*/
}
