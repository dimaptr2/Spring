package ru.velkomfood.acs.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by velkomfood on 17.02.17.
 */
@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {

        System.getProperties().put("server.port", 8183);
        SpringApplication.run(Launcher.class, args);

    }

}
