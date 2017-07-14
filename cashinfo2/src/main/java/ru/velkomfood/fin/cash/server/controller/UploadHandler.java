package ru.velkomfood.fin.cash.server.controller;

import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.velkomfood.fin.cash.server.persistence.DataFather;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

/**
 * Created by dpetrov on 23.06.17.
 */

@Component
public class UploadHandler {

    @Autowired
    private DataFather dataFather;

    @PostConstruct
    public void initDatabaseStatus() throws JCoException, SQLException  {
        dataFather.takeNewDictionaries();
    }

//    Run every day at 22:15 o'clock
    @Scheduled(cron = "0 15 22 * * *")
    public void uploadMasterData() {

        try {
            dataFather.updateDictionaries();
        } catch (JCoException | SQLException ex1) {
            ex1.printStackTrace();
        }

    } // upload the master data

//    representing: second, minute, hour, day, month, weekday
    // Start every day at 1 minute at 7-20 hours
    @Scheduled(cron = "0 30 18 * * *")
    public void uploadTransactionData() {

        System.out.println("Ok! Start uploading task!");

        try {
            dataFather.takeNewDocuments();
        } catch (SQLException | JCoException ex2) {
            ex2.printStackTrace();
        }

    } // upload the transaction data

    @Scheduled(cron = "0 2 0 * * *")
    public void uploadPeriodic() {

        try {
            dataFather.doDailyUploading();
        } catch (JCoException | SQLException e) {
            e.printStackTrace();
        }

    }

}
