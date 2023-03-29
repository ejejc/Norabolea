package com.example.jpamaster.common.security;

import com.example.jpamaster.users.domain.User;
import com.example.jpamaster.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(1L).get();

        //CustomUserDetails customUserDetails = new CustomUserDetails(user);


        //return customUserDetails;
        return null;
    }
}