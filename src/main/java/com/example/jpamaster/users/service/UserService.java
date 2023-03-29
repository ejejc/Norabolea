package com.example.jpamaster.users.service;

import com.example.jpamaster.users.domain.User;
import com.example.jpamaster.users.dto.UserDto;
import com.example.jpamaster.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void addUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .pwd(userDto.getPwd())
                .birth(userDto.getBirth())
                .role(userDto.getRole()).build();
        userRepository.save(user);
    }
}
