package com.example.jpamaster.common.security.oauth2;

import com.example.jpamaster.users.domain.User;
import com.example.jpamaster.users.repository.UserRepository;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String registrationId = clientRegistration.getRegistrationId();

        Assert.notNull(registrationId, "registration must be not null");

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String userNameAttributeName = clientRegistration.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes userAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, attributes);

        User user = saveOrUpdateUser(userAttributes);

        return CustomUserPrincipal.create(
            user.getUserSeq(),
            clientRegistration.getRegistrationId(),
            user.getName(),
            user.getEmail(),
            user.getPicture(),
            attributes,
            Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey()))
        );
    }

    private User saveOrUpdateUser(final OAuthAttributes userAttributes) {
        Optional<User> optionalUser = userRepository.findByEmail(userAttributes.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.update(userAttributes.getUsername(), userAttributes.getPicture());
            return user;
        }

        User user = userAttributes.toEntity();
        userRepository.save(user);

        return user;
    }

}
