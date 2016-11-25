package ru.velkomfood.production.beaglebone.infosys.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

/**
 * Created by dpetrov on 10.06.16.
 */

public class BeagleBoneTester {

    private final String IP = "10.200.204.75";
    private final String URL_NAME = "http://10.200.204.75:8081/boxinfo";


    // Checking that the beaglebone is alive
    public boolean checkIpAddress() throws IOException {

        InetAddress inet = InetAddress.getByName(IP);

        if (inet.isReachable(5000)) {
            System.out.println("IP address of Beaglebone is Ok " + IP);
            return true;
        }
        else {
            System.out.println("IP address "
                    + IP + " is not reachable");
            return false;
        }

    }

    // Checking that Jetty server is alive on the beaglebone
    public boolean checkHttpRequest() throws IOException {


        URL url = new URL(URL_NAME);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (connection != null) {

            connection.setRequestMethod("POST");
            connection.disconnect();
            System.out.println("HTTP server is worked " + URL_NAME);
            return true;

        } else {

            System.out.println("Fail! Fail! Fail! HTTP server is not worked");
            return false;

        }


    }

}
