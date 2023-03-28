package com.example.jpamaster.users.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserEnums {

    @Getter
    @RequiredArgsConstructor
    public enum Role {
        USER("ROLE_USER", "일반 사용자"),
        ADMIN("ROLE_ADMIN", "관리자")
        ;

        private final String key;
        private final String title;
    }

    @Getter
    @RequiredArgsConstructor
    public enum AuthProvider {
        GOOGLE("google"),
        NAVER("naver"),
        KAKAO("kakao"),
        GITHUB("github"),
        META("meta"),
        OKTA("okta"),
        ;

        private final String providerType;
    }

}
