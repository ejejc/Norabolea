package com.example.jpamaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.jpamaster")
@EntityScan(basePackages = "com.example.jpamaster")
@EnableJpaRepositories(basePackages = "com.example.jpamaster")
@EnableFeignClients
public class JpaMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaMasterApplication.class, args);
    }

}
