package com.neemroz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync   // required for @Async in EmailService
public class NeemrozApplication {
    public static void main(String[] args) {
        SpringApplication.run(NeemrozApplication.class, args);
    }
}
