package com.example.jpamaster.users.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    @GetMapping("/")
    public String login() {
        return "hi";
    }
    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }
}
