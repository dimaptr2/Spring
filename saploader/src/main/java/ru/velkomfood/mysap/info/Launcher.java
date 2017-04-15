package ru.velkomfood.mysap.info;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by dpetrov on 15.04.17.
 */
@SpringBootApplication
@EnableScheduling
public class Launcher {

    public static void main(String[] args) {

        System.out.println("Start SAP reader server on the port 9192");
        System.out.println("Start task for the uploading data from SAP");

    }

}
