package com.example.jpamaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpaMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaMasterApplication.class, args);
    }

}
