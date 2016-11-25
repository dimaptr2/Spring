package ru.velkomfood.production.beaglebone.infosys.controller;

import java.util.List;

/**
 * Created by dpetrov on 11.06.16.
 */
public class MailSender {

    private List<String> hosts;
    private List<String> receivers;
    private String path;
    private final String CONFIG_FILE = "/appconfig/mailsender.properties";

    public MailSender() {

        path = System.getProperty("user.dir");
        path.replace("\\\\", "/");
        path += CONFIG_FILE;

    }

    public void initMailSenderProperties() {

    }
}
