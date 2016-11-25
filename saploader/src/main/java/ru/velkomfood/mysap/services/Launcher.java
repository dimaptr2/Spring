package ru.velkomfood.mysap.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Launcher {

	// Main starter
	public static void main(String[] args) throws Exception {

		// Set system port
		System.getProperties().put("server.port", 9091);

		// Run an event handler and a servlet container
	    SpringApplication.run(Launcher.class, args);

	}
	
}