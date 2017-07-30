package ru.velkomfood.fin.cash.server.persistence;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.fin.cash.server.model.master.Material;
import ru.velkomfood.fin.cash.server.model.transaction.CashDocument;
import ru.velkomfood.fin.cash.server.model.transaction.DeliveryHead;
import ru.velkomfood.fin.cash.server.model.transaction.DeliveryItem;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by dpetrov on 27.06.17.
 */

@Component
public class DataFather {

    @Autowired
    private SAPEngine sapEngine;
    @Autowired
    private  DBEngine dbEngine;

    public void takeNewDictionaries() throws SQLException, JCoException {

        final BigDecimal UNIT = new BigDecimal(1.00);

        long t1 = new Date().getTime();

        // We should get documents for last week before and for today

        System.out.println("Start database initialization");

        if (dbEngine.checkTableIsEmpty("materials")) {

            long n1 = sapEngine.readAllMaterialsFromSAP();
            System.out.printf("Number materials %d\n", n1);

            // Add a fake material for calculations
            Material m = new Material(999999999, "Округления",
                    "ST", UNIT, UNIT, 0);
            dbEngine.saveMaterial(m);

            long n2 = sapEngine.readAllPartnersFromSAP();
            System.out.printf("Number partners %d\n", n2);

        }

        long t2 = new Date().getTime();
        showMessageAboutExecutionTime(t1, t2);

    }

    public void updateDictionaries() throws SQLException, JCoException {

        long n1 = sapEngine.readAllMaterialsFromSAP();
        System.out.printf("Number materials %d\n", n1);

        long n2 = sapEngine.readAllPartnersFromSAP();
        System.out.printf("Number partners %d\n", n2);

    }

//    public void takeNewDocuments() throws SQLException, JCoException {
//
//        dbEngine.refreshTransactionData();
//
//        long dt1 = new Date().getTime(); // Current day in milliseconds
//        final long DAY_IN_MS = 86400000; // A day in milliseconds
//
//        long dates[] = new long[30];
//
//        for (int i = 29; i >= 0; i--) {
//            dates[i] = dt1;
//            dt1 = dt1 - DAY_IN_MS;
//        }
//
//        for (long value: dates) {
//            java.sql.Date d = new java.sql.Date(value);
//            getCashDocuments(d, d);
//            calculateTotalAmount(d);
//        }
//
//    }


//    public void doDailyUploading() throws SQLException, JCoException {
//
//        long now = new Date().getTime();
//        java.sql.Date d = new java.sql.Date(now);
//        getCashDocuments(d, d);
//        calculateTotalAmount(d);
//
//    }

    // get transaction data
    public void takeTransactionData(boolean daily) throws JCoException, SQLException {

        List<CashDocument> docs;

        long mom = new Date().getTime();

        long t1 = new Date().getTime();

        try {

            JCoContext.begin(sapEngine.getDestination());

            if (daily) {

                java.sql.Date dt = new java.sql.Date(mom);
                dbEngine.saveCashDocumentsByQueue(
                        sapEngine.readCashDocumentsBetweenDates(dt, dt)
                );
                docs = dbEngine.readCashDocumentsByDateBetween(dt, dt);

            } else {

                long dates[] = buildDateRange(mom);
                for (long t : dates) {
                    java.sql.Date dt = new java.sql.Date(t);
                    dbEngine.saveCashDocumentsByQueue(
                            sapEngine.readCashDocumentsBetweenDates(dt, dt)
                    );
                }
                LocalDate now = LocalDate.now();
                docs = dbEngine.readCashDocumentsByYearBetween(now.getYear(), now.getYear());

            }

            sapEngine.readDeliveriesByCashDocuments(docs);

        } finally {

            JCoContext.end(sapEngine.getDestination());

        }

        long t2 = new Date().getTime();

        System.out.println("Transaction data is uploaded");
        showMessageAboutExecutionTime(t1, t2);

    }

    private long[] buildDateRange(long value) {

        final long DAY_IN_MS = 86400000; // A day in milliseconds

        long dates[] = new long[30];

        for (int i = 29; i >= 0; i--) {
            dates[i] = value;
            value = value - DAY_IN_MS;
        }

        return dates;
    }

    private void showMessageAboutExecutionTime(long m1, long m2) {

        long delta = calculateInterval(m1, m2);
        String timeUnit = "sec";

        if (delta > 60) {
            timeUnit = "min";
            delta /= 60;
            if (delta > 60) {
                timeUnit = "hours";
                delta /= 60;
            }
        }

        System.out.printf("Time of execution is %d %s\n", delta, timeUnit);
    }


    // Calculate the time interval in the seconds
    private long calculateInterval(long x1, long x2) {
        return (x2 - x1) / 1000;
    }

}

