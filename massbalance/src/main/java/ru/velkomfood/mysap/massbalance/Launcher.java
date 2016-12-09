package ru.velkomfood.mysap.massbalance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by dpetrov on 09.12.16.
 */

@SpringBootApplication
public class Launcher {

    public static void main(String[] args) throws Exception {

        final int PORT = 9190;

        System.getProperties().put("server.port", PORT);
        SpringApplication.run(Launcher.class);

    }

}
