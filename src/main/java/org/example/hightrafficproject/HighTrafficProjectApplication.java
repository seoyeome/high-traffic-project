package org.example.hightrafficproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HighTrafficProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HighTrafficProjectApplication.class, args);
    }

}
