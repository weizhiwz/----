package com.example.backweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class BackwebApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackwebApplication.class, args);
    }
}
