package org.example.userserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.example.userserviceapi.entity")
public class UserServiceApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApiApplication.class, args);
    }
}
