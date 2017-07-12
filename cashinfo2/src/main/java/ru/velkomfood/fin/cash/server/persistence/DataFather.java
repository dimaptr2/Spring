package ru.velkomfood.fin.cash.server.persistence;

import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.fin.cash.server.model.master.Material;
import ru.velkomfood.fin.cash.server.model.transaction.DeliveryHead;
import ru.velkomfood.fin.cash.server.model.transaction.DeliveryItem;

import java.math.BigDecimal;
import java.sql.SQLException;
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


    public void takeNewDocuments() throws SQLException, JCoException {

        long dt1 = new Date().getTime(); // Current day in milliseconds
        final long DAY_IN_MS = 86400000; // A day in milliseconds

        long dates[] = new long[8];

        for (int i = 7; i >= 0; i--) {
            dates[i] = dt1;
            dt1 = dt1 - DAY_IN_MS;
        }

        for (long value: dates) {
            java.sql.Date d = new java.sql.Date(value);
            getCashDocuments(d, d);
        }

    }

    public void updateDictionaries() throws SQLException, JCoException {

        long n1 = sapEngine.readAllMaterialsFromSAP();
        System.out.printf("Number materials %d\n", n1);

        long n2 = sapEngine.readAllPartnersFromSAP();
        System.out.printf("Number partners %d\n", n2);

    }

    public void doDailyUploading() throws SQLException, JCoException {

        long now = new Date().getTime();
        java.sql.Date d = new java.sql.Date(now);
        getCashDocuments(d, d);

    }

    public void processNullAmounts() {

        Map<Long, List<DeliveryItem>> deliveryMap = dbEngine.readDeliveriesWithNullAmount();

        if (deliveryMap != null && !deliveryMap.isEmpty()) {
            Iterator<Map.Entry<Long, List<DeliveryItem>>> it = deliveryMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Long, List<DeliveryItem>> entry = it.next();
                long key = entry.getKey();
                List<DeliveryItem> value = entry.getValue();
                if (value != null && !value.isEmpty()) {
                    DeliveryHead head = dbEngine.readDeliveryHeadByKey(key);
                    if (head != null) {
                        BigDecimal amount = calculateNetSum(value);
                        head.setTotalAmount(amount);
                        dbEngine.saveDeliveryHead(head);
                    }
                }
            }
        }

    }

    public void processReturningDeliveries(java.sql.Date dt1, java.sql.Date dt2) {

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

    // Get all documents
    private void getCashDocuments(java.sql.Date d1, java.sql.Date d2) throws SQLException, JCoException {

        sapEngine.readCashDocumentsByDate(d1, d2);
        deleteNullableQuantities();

    }

    private void deleteNullableQuantities() throws SQLException {
        dbEngine.deleteZerosDeliveryItems();
    }


    private BigDecimal calculateNetSum(List<DeliveryItem> listItems) {

        BigDecimal sum = new BigDecimal(0.00);

        for (DeliveryItem di: listItems) {
            sum = sum.add(di.getNetPrice());
        }

        return sum;
    }

    private BigDecimal calculateGrossSum(List<DeliveryItem> listItems) {

        BigDecimal sum = new BigDecimal(0.00);
        // Rates of VAT
        final BigDecimal VAT10 = new BigDecimal(0.10);
        final BigDecimal VAT18 = new BigDecimal(0.18);
        final BigDecimal UNIT = new BigDecimal(1.00);

        for (DeliveryItem di: listItems) {

            switch (di.getVatRate()) {
                case 10:
                    sum = sum.add(di.getPrice().multiply(di.getQuantity()).multiply(UNIT.add(VAT10)));
                    break;
                case 18:
                    sum = sum.add(di.getPrice().multiply(di.getQuantity()).multiply(UNIT.add(VAT18)));
                    break;
            }

        }

        return sum;
    }

}

