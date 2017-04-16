package ru.velkomfood.mysap.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by dpetrov on 15.04.17.
 */
@Component
public class TaskHandler {

    @Autowired
    private DataReader dataReader;
    @Autowired
    private DataWriter dataWriter;

    // (sec, min, hour, day, month, day week)
    @Scheduled(cron = "*/10 * * * * *")
    public void uploadInfo() {

        if (dataReader != null && dataWriter != null) {
            System.out.println("Start task for the uploading data from SAP");
        } else {
            System.out.println("Main service was not created");
        }


    }

}
