package com.example.jpamaster.users.domain;

import com.example.jpamaster.common.domain.BaseEntity;
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

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "picture")
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public void update(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }
}
