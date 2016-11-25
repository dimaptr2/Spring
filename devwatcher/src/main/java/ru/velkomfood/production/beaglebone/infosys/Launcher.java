package ru.velkomfood.production.beaglebone.infosys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by dpetrov on 10.06.16.
 */

@SpringBootApplication
@EnableScheduling
public class Launcher {

    public static void main(String[] args) throws Exception {

        // Set system port
        System.getProperties().put("server.port", 9092);

        // Run this application
        SpringApplication.run(Launcher.class, args);

    }

}
