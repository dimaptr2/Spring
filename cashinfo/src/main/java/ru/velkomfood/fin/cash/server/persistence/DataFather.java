package ru.velkomfood.fin.cash.server.persistence;

import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.velkomfood.fin.cash.server.model.master.Material;
import ru.velkomfood.fin.cash.server.model.transaction.CashDocument;
import ru.velkomfood.fin.cash.server.model.transaction.DeliveryHead;
import ru.velkomfood.fin.cash.server.model.transaction.DeliveryItem;
import ru.velkomfood.fin.cash.server.model.transaction.DistributedItem;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

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

        java.sql.Date d1 = new java.sql.Date(dt1);
        java.sql.Date d2 = new java.sql.Date(dt1 - DAY_IN_MS);

        getCashDocuments(d1, d1);
        getCashDocuments(d2, d2);

    }

    public void updateDictionaries() throws SQLException, JCoException {

        long n1 = sapEngine.readAllMaterialsFromSAP();
        System.out.printf("Number materials %d\n", n1);

        long n2 = sapEngine.readAllPartnersFromSAP();
        System.out.printf("Number partners %d\n", n2);

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
        processLatestItem(d1, d2);

    }

    private void deleteNullableQuantities() throws SQLException {
        dbEngine.deleteZerosDeliveryItems();
    }

    private void processLatestItem(java.sql.Date dt1, java.sql.Date dt2) {

        List<CashDocument> docs = dbEngine.readCashDocumentsByDateBetween(dt1, dt2);

        if (docs != null && !docs.isEmpty()) {

            docs.forEach(row -> {

                List<DeliveryItem> items = dbEngine.readDeliveryItemsByKey(row.getDeliveryId());

                if (items != null && !items.isEmpty()) {

                    double totalAmount = calculateSum(items);
                    double ratio = (totalAmount - row.getAmount().doubleValue()) / totalAmount;
                    double grossPrice = 0.00;

                    BigDecimal value = BigDecimal.valueOf(ratio)
                            .setScale(4, BigDecimal.ROUND_FLOOR);

                    for (DeliveryItem di : items) {
                        grossPrice += di.getNetPrice().add(di.getVat()).doubleValue();
                        DistributedItem disit = new DistributedItem();
                        disit.setId(di.getId());
                        disit.setPosition(di.getPosition());
                        disit.setMaterialId(di.getMaterialId());
                        disit.setDescription(di.getDescription());
                        disit.setQuantity(di.getQuantity().multiply(value));
                        disit.setPrice(di.getPrice());
                        double coefficient = 1 - (di.getPrice().doubleValue() / totalAmount);
                        double q = coefficient * disit.getQuantity().doubleValue();
                        disit.setQuantity(BigDecimal.valueOf(q).setScale(3, BigDecimal.ROUND_FLOOR));
                        disit.setVat(di.getVat());
                        disit.setVatRate(di.getVatRate());
                        dbEngine.saveDistributedItem(disit);
                    }

                    DeliveryHead head = dbEngine.readDeliveryHeadByKey(row.getDeliveryId());

                    if (head != null) {
                        head.setTotalAmount(BigDecimal.valueOf(grossPrice)
                                .setScale(2, BigDecimal.ROUND_FLOOR));
                        dbEngine.saveDeliveryHead(head);
                    }

                } // if items

            });

        }

    }


    private double calculateSum(List<DeliveryItem> listItems) {

        BigDecimal sum = new BigDecimal(0.00);

        Iterator<DeliveryItem> iterator = listItems.iterator();

        while (iterator.hasNext()) {
            DeliveryItem di = iterator.next();
            BigDecimal value = di.getQuantity().multiply(di.getPrice());
            sum = sum.add(value.add(di.getVat()));
        }

        return sum.doubleValue();
    }

}

