package ru.velkomfood.copc.ckm.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by dpetrov on 25.08.2016.
 */

@SpringBootApplication
@EnableScheduling
public class Launcher {

    public static void main(String[] args) throws Exception {

        System.getProperties().put("server.port", 9494);
        SpringApplication.run(Launcher.class, args);

    }

}
