package com.example.jpamaster.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Table(
    name = "login_token",
    indexes = {
        @Index(name = "idx_login_access_token", columnList = "access_token"),
        @Index(name = "idx_login_refresh_token", columnList = "refresh_token"),
    }
)
@Entity
public class LoginToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenSeq;

    @Comment("로그인 사용자 jwt access token")
    @Column(name = "access_token")
    private String accessToken;

    @Comment("로그인 사용자 jwt refresh token")
    @Column(name = "refresh_token")
    private String refreshToken;

}
