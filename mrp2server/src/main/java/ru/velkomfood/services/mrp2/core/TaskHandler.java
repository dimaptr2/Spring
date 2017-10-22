package ru.velkomfood.services.mrp2.core;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.velkomfood.services.mrp2.model.Material;

import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class TaskHandler {

    @Autowired
    private SapReader sapReader;
    @Autowired
    private DbWriter dbWriter;
    @Autowired
    private AlphaTransformer alphaTransformer;

    @Scheduled(cron = "0 5 7 * * *")
    public void uploadMasterData() {

        long t1 = new Date().getTime();

        System.out.println("Start the master data uploading");

        try {
            sapReader.readMaterials();
        } catch (JCoException sapex) {
            sapex.printStackTrace();
        }

        System.out.println("Finish the master data uploading");

        long t2 = new Date().getTime();

        showMessageAboutExecution(t1, t2);

    }

    @Scheduled(cron = "0 1 */2 * * *")
    public void uploadTransactionData() {

        long t1 = new Date().getTime();

        System.out.println("Start the transaction data uploading");

        List<Material> materials = dbWriter.readAllMaterials();
        takeRequirementsList(materials);

        System.out.println("Finish the transaction data uploading");

        long t2 = new Date().getTime();

        showMessageAboutExecution(t1, t2);

    }


    private void takeRequirementsList(List<Material> materialList) {

        JCoDestination dest = sapReader.getDestination();

        if (!materialList.isEmpty()) {

            LocalDate moment = LocalDate.now();
            String year = String.valueOf(moment.getYear());

            Iterator<Material> it = materialList.iterator();

            while (it.hasNext()) {

                Material material = it.next();
                long id = material.getId();

                try {

                    JCoContext.begin(dest);

                    int startPoint = moment.getMonthValue();

                    while (startPoint >= 1) {
                        String month = alphaTransformer.transformMonthToString(startPoint);
                        sapReader.readStocksByMaterialIdAndPeriod(year, month, id);
                        startPoint--;
                    }

                    sapReader.readRequirementsByMaterialId(id);

                } catch (JCoException e1) {

                    continue;

                } finally {

                    try {
                        JCoContext.end(dest);
                    } catch (JCoException e2) {
                        e2.printStackTrace();
                    }

                }

            } // end of while

        }

    }

    private void showMessageAboutExecution(long v1, long v2) {

        long delta = (v2 - v1) / 1000;

        String unit = "sec";

        if (delta > 60) {
            unit = "min";
            delta /= 60;
            if (delta > 60) {
                unit = "hours";
                delta /= 60;
            }
        }

        String message = String.format("Time of execution is %d %s\n", delta, unit);
        System.out.append(message);

    }


}
