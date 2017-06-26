package ru.velkomfood.fin.cash.server.controller;

import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.velkomfood.fin.cash.server.persistence.SAPEngine;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by dpetrov on 23.06.17.
 */

@Component
public class UploadHandler {

    @Autowired
    private SAPEngine sapEngine;

    @PostConstruct
    public void initDatabaseStatus() throws JCoException, SQLException  {

        System.out.println("Start database initialization");

        long n1 = sapEngine.readAllMaterialsFromSAP();
        System.out.printf("Number materials is %d\n", n1);

        long n2 = sapEngine.readAllPartnersFromSAP();
        System.out.printf("Number materials is %d\n", n2);

    }

//    Run every day at 22:00 o'clock
    @Scheduled(cron = "0 0 22 * * *")
    public void uploadMasterData() {

        long t1 = new Date().getTime();
        int numberMaterials;
        int numberPartners;

        System.out.println("Upload master data from SAP");

        try {

            numberMaterials = sapEngine.readAllMaterialsFromSAP();
            System.out.printf("Number materials is %d\n", numberMaterials);

            numberPartners = sapEngine.readAllPartnersFromSAP();
            System.out.printf("Number partners is %d\n", numberPartners);

        } catch (SQLException | JCoException ex) {

            ex.printStackTrace();

        }

        long t2 = new Date().getTime();

        long delta = (t2 - t1) / 1000;

        String timeUnit;

        if (delta >=1 && delta <= 60) {

            timeUnit = "sec";

        } else {

            delta = delta / 60;

            if (delta >= 1 && delta <= 60) {
                timeUnit = "min";
            } else {
                delta = delta / 60;
                timeUnit = "hours";
            }

        }

        System.out.printf("Time of execution is %d %s\n", delta, timeUnit);

    } // upload the master data

//    representing: second, minute, hour, day, month, weekday
    // Start every day at 8:00, 9:00, 10:00 go through 18:00 o'clock
    @Scheduled(cron = "0 0 8-18 * * 1-5")
    public void uploadTransactionData() {

        System.out.println("Ok! Start uploading task!");

    } // upload the transaction data

}
