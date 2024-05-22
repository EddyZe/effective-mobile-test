package ru.effective.clientapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan("ru.effective.*")
@EnableScheduling
public class ClientApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApiApplication.class, args);
    }
}
