package org.iegs.services.books.phones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by petrovdmitry on 10.07.16.
 */


@SpringBootApplication
public class Launcher {

    public static void main(String[] args) throws Exception {

        System.getProperties().put("server.port", 9191);

        SpringApplication.run(Launcher.class, args);

    }

}
