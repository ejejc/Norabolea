package com.example.jpamaster.common.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class CommonController {

    @GetMapping("/health")
    public ResponseEntity<String> healthy(HttpServletRequest request) {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/unhealth")
    public String unhealthy() {
        return "Not OK";
    }

    @GetMapping("/denied")
    public String denied() {
        return "Access Denied";
    }
}
