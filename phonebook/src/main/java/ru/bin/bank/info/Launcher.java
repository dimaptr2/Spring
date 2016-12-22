package ru.bin.bank.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher {

	public static void main(String[] args) {
		System.getProperties().put("server.port", 9495);
		SpringApplication.run(Launcher.class, args);
	}

}
