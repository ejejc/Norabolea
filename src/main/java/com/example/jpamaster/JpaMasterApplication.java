package com.example.jpamaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
public class JpaMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaMasterApplication.class, args);
    }

}
