package com.ertu.weddingplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class WeddingplannerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeddingplannerApplication.class, args);
    }

}
