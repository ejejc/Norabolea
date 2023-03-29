package com.example.jpamaster.users.dto;

import com.example.jpamaster.users.enums.UserEnums;
import lombok.Getter;

@Getter
public class UserDto {
    private String name;
    private String id;
    private String pwd;
    private String birth;
    private UserEnums.Role role;
}
