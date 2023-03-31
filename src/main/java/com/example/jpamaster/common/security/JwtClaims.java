package com.example.jpamaster.common.security;

import com.example.jpamaster.users.enums.UserEnums;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class JwtClaims {
    private Long userSeq;
    private String birth;
    private List<UserEnums.Role> roles;
}
