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

        long yesterday = dt1 - DAY_IN_MS;

        if (dbEngine.readCashDocumentsByDate(new java.sql.Date(yesterday)).isEmpty()) {

            java.sql.Date to = new java.sql.Date(dt1);
            java.sql.Date from = new java.sql.Date((dt1 - (30 * DAY_IN_MS)));
            getCashDocuments(from, to);

        } else {

            java.sql.Date currentDate = new java.sql.Date(dt1);
            getCashDocuments(currentDate, currentDate);

        }

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
                Queue<DeliveryItem> cache = new ConcurrentLinkedQueue<>();

                if (items != null && !items.isEmpty()) {

                    double totalAmount = calculateSum(items);
                    double ratio = row.getAmount().doubleValue() / totalAmount;

                    BigDecimal value = BigDecimal.valueOf(ratio)
                            .setScale(4, BigDecimal.ROUND_FLOOR);
                    BigDecimal sum = new BigDecimal(0.00);

                    for (DeliveryItem di : items) {
                        DistributedItem disit = new DistributedItem();
                        disit.setId(di.getId());
                        disit.setPosition(di.getPosition());
                        disit.setMaterialId(di.getMaterialId());
                        disit.setDescription(di.getDescription());
                        disit.setQuantity(di.getQuantity().multiply(value));
                        disit.setPrice(di.getPrice());
                        sum = sum.add(disit.getQuantity().multiply(disit.getPrice()))
                                .setScale(2, BigDecimal.ROUND_FLOOR);
                        disit.setVat(di.getVat());
                        disit.setVatRate(di.getVatRate());
                        dbEngine.saveDistributedItem(disit);
                    }

                    BigDecimal result = sum.subtract(row.getAmount());

                    Material material = dbEngine.readMaterialByKey(999999999);
                    if (material != null) {
                        DistributedItem dit = new DistributedItem();
                        dit.setId(row.getDeliveryId());
                        dit.setPosition(material.getId());
                        dit.setMaterialId(material.getId());
                        dit.setDescription(material.getDescription());
                        dit.setQuantity(material.getPriceUnit());
                        dit.setPrice(result);
                        dit.setVat(new BigDecimal(0.00));
                        dit.setVatRate(0);
                        dbEngine.saveDistributedItem(dit);
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
            sum = sum.add(di.getQuantity().multiply(di.getPrice()));
        }

        return sum.doubleValue();
    }


}

