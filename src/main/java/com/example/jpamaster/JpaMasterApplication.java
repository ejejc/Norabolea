package com.example.jpamaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.jpamaster")
public class JpaMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaMasterApplication.class, args);
    }

}
