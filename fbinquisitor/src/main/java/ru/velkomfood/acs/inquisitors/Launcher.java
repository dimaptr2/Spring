package ru.velkomfood.acs.inquisitors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Created by Velkomfood on 27.06.16.
 */

@SpringBootApplication
@EnableScheduling
public class Launcher {

    public static void main(String[] args) throws Exception {

        System.getProperties().put("server.port", 9191);
        SpringApplication.run(Launcher.class, args);

    }

}
