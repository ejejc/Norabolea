package com.example.jpamaster.users.controller;

import com.example.jpamaster.users.domain.User;
import com.example.jpamaster.users.dto.UserDto;
import com.example.jpamaster.users.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
