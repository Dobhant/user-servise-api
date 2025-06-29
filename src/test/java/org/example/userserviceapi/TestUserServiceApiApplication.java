package org.example.userserviceapi;

import org.springframework.boot.SpringApplication;

public class TestUserServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(UserServiceApiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
