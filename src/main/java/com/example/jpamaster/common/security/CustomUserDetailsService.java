package com.example.jpamaster.common.security;

import com.example.jpamaster.users.domain.User;
import com.example.jpamaster.users.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElse(null);
        if (ObjectUtils.isEmpty(user)) {
            throw new InternalAuthenticationServiceException("유저 정보가 존재하지 않습니다.");
        }
        return new CustomUserDetails(user, List.of(user.getRole()));
    }
    public UserDetails loadUserByUserSeq(Long userSeq) {
        User user = userRepository.findById(userSeq).orElse(null);
        return new CustomUserDetails(user, List.of(user.getRole()));
    }
}
