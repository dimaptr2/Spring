package ru.velkomfood.services.mrp2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Launcher {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Launcher.class);
    }

}
