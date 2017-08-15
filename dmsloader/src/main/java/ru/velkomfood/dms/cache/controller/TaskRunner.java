package ru.velkomfood.dms.cache.controller;

import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.velkomfood.dms.cache.model.DMSdocument;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskRunner {

    @Autowired
    private DataMaker dataMaker;

    @Scheduled(cron = "0 49 17 * * 1-5")
    public void uploadDMSdata() {

        try {

            dataMaker.takeDocuments();
            List<DMSdocument> keys = dataMaker.getAllDocumentNumbers();
            List<List<DMSdocument>> parts = buildPartsForProcessing(keys);
            int step = 0;
            for (List<DMSdocument> part: parts) {
                step++;
                dataMaker.takeDetails(part, step);
            }

            System.out.println("End all uploading tasks");

        } catch (JCoException | InterruptedException ex) {

            ex.printStackTrace();

        }

    }

    private List<List<DMSdocument>> buildPartsForProcessing(List<DMSdocument> ids) {

        List<List<DMSdocument>> lists = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            if ((i % 1000) == 0) {
                int next = i + 999;
                if (next < ids.size()) {
                    lists.add(ids.subList(i, next));
                } else {
                    lists.add(ids.subList(i, (ids.size() - 1)));
                }

            }
        }

        return lists;
    }
}
