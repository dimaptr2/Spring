package ru.velkomfood.services.mrp2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Launcher {

    public static void main(String[] args) throws Exception {

        // Set the local path to the native libraries
        System.setProperty("java.library.path", "/usr/sap/JCo");
        // Run this application
        SpringApplication.run(Launcher.class);

    }

}
