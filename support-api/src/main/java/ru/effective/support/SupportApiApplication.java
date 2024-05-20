package ru.effective.support;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("ru.effective.*")
public class SupportApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupportApiApplication.class, args);
    }

}
