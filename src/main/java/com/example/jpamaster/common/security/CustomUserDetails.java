package com.example.jpamaster.common.security;

import com.example.jpamaster.users.domain.User;
import com.example.jpamaster.users.enums.UserEnums;
import java.util.stream.Collectors;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private User user;
    private List<UserEnums.Role> roles;

    public CustomUserDetails(User user, List<UserEnums.Role> roles) {
        this.user = user;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(vo -> new SimpleGrantedAuthority(vo.getKey())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPwd();
    }

    @Override
    public String getUsername() {
        return this.user.getId();
    }

    // 사용자 계정이 만료되었는지 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자가 잠겼는지 자금 해제되었는지 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자의 자격 증명 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자의 활성화 또는 비활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }

    /*public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }*/
}
