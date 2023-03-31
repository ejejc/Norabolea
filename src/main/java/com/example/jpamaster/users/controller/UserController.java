package com.example.jpamaster.users.controller;

import com.example.jpamaster.users.dto.UserDto;
import com.example.jpamaster.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @PostMapping("/create")
    public void createUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }
}
