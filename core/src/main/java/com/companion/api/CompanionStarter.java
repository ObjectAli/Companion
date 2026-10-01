package com.companion.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {
        "com.companion.feature",
        "com.companion.security",
        "com.companion.web", "com.companion.web.cors"
})
public class CompanionStarter {

    public static void main(String[] args) {
        SpringApplication.run(CompanionStarter.class, args);
    }
}
