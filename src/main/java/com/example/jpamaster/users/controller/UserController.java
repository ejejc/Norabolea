package com.example.jpamaster.users.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/google/callback")
    public void callback() {
        System.out.println(" = ");
    }
}
