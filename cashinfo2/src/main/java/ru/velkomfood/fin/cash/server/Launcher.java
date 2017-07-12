package ru.velkomfood.fin.cash.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by dpetrov on 22.06.17.
 */
@SpringBootApplication
@EnableScheduling
public class Launcher {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(Launcher.class, args);

    }

}
