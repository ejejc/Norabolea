package com.example.jpamaster.users.domain;

import com.example.jpamaster.common.domain.BaseEntity;
import com.example.jpamaster.users.enums.UserEnums.AuthProvider;
import com.example.jpamaster.users.enums.UserEnums.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userSeq;

    @Column(name = "userId")
    private String id;

    @Column(name = "password")
    private String pwd;

    @Column(name = "birth")
    private String birth;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "picture")
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthProvider authProvider;


    @Builder
    public User(String name, String email, String picture, Role role, AuthProvider authProvider
            , String id, String pwd, String birth) {
        this.name = name;
        this.id = id;
        this.pwd = pwd;
        this.birth = birth;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.authProvider = authProvider;
    }


    public void update(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }
}
