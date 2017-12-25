package ru.velkomfood.mrp3.reports.controller;

import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.velkomfood.mrp3.reports.model.md.Material;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class DataHandler {

    @Autowired
    private ModeIndicator indicator;
    @Autowired
    private SapReader sapReader;
    @Autowired
    private DbChanger dbChanger;

    @Scheduled(cron = "0 52 13 * * *")
    public void refreshData() {

        if (!indicator.isLoading()) {
            indicator.setLoading(true);
        }

        long t1 = new Date().getTime();

        try {

            sapReader.readPurchaseStructure();
            List<Material> mats = takeMasterData();
            takeTransactionData(mats);
            calculateResultData();
            dbChanger.findAndSaveCurrentStock();
            indicator.setLoading(false);

        } catch (JCoException jcoe) {

            indicator.setLoading(false);
            jcoe.printStackTrace();

        }

        long t2 = new Date().getTime();
        showMessageAboutExecution(t1, t2);

    }

//    @Scheduled(cron = "0 44 14 * * *")
//    public void readCurrentStocks() {
//        dbChanger.findCurrentStock();
//    }

    // Read the materials dictionary
    private List<Material> takeMasterData() throws JCoException {
            return sapReader.readAllMaterials();
    }

    // Read the stocks and the requirements
    private void takeTransactionData(List<Material> mats) throws JCoException {

        Iterator<Material> it = mats.iterator();

        while (it.hasNext()) {
            Material material = it.next();
            sapReader.readStocksAndRequirementsByMaterialId(material.getId());
        }

        mats.clear();
    }

    // Calculate the resulting data
    private void calculateResultData() {
        dbChanger.buildResultingData();
    }

    private void showMessageAboutExecution(long v1, long v2) {

        long delta = (v2 - v1) / 1000; // in seconds
        String unit = "seconds";

        if (delta > 59) {
            unit = "minutes";
            delta /= 60;
            if (delta > 59) {
                unit = "hours";
                delta /= 60;
            }
        }

        String message = String.format("Time of execution is %d %s", delta, unit);
        System.out.println(message);

    }

}
