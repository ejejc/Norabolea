package com.example.jpamaster.common.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @GetMapping("/health")
    public String healthy() {
        return "OK";
    }

    @GetMapping("/unhealth")
    public String unhealthy() {
        return "Not OK";
    }
}
