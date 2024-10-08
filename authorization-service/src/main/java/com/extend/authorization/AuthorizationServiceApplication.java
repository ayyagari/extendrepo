package com.extend.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AuthorizationServiceApplication {

    private int age;

    public static void main(String[] args) throws CloneNotSupportedException {
        SpringApplication.run(AuthorizationServiceApplication.class, args);
    }

}