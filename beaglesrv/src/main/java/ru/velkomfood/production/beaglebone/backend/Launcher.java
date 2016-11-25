package ru.velkomfood.production.beaglebone.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher {

  public static void main(String[] args) throws Exception {

    System.getProperties().put("server.port", 9093);

    SpringApplication.run(Launcher.class, args);

  }
  
}
