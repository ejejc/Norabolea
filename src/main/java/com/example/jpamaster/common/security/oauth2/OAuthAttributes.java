package com.example.jpamaster.common.security.oauth2;


import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.users.domain.User;
import com.example.jpamaster.users.enums.UserEnums.AuthProvider;
import com.example.jpamaster.users.enums.UserEnums.Role;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
    private final String nameAttributeKey;
    private final String registrationId;
    private final String username;
    private final String email;
    private final String picture;

    @Builder
    private OAuthAttributes(String nameAttributeKey, String registrationId,
        String username, String email, String picture) {
        this.nameAttributeKey = nameAttributeKey;
        this.registrationId = registrationId;
        this.username = username;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equalsIgnoreCase(registrationId)) {
            return ofNaver("id", registrationId, attributes);
        } else if ("google".equalsIgnoreCase(registrationId)) {
            return ofGoogle(userNameAttributeName, registrationId, attributes);
        }
        throw new OAuth2AuthenticationProcessingException(
            String.format("OAuth2 provider not supported : %s", registrationId),
            HttpStatusCode.BAD_REQUEST
        );
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, String registrationId, Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .username((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, String registrationId, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .username((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .picture((String) attributes.get("picture"))
            .nameAttributeKey(userNameAttributeName)
            .registrationId(registrationId)
            .build();
    }

    public User toEntity() {
        return User.builder()
            .name(username)
            .email(email)
            .picture(picture)
            .role(Role.USER)
            .authProvider(AuthProvider.valueOf(registrationId.toUpperCase()))
            .build();
    }
}

