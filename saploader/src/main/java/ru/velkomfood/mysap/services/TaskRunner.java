package ru.velkomfood.mysap.services;

import com.sap.conn.jco.JCoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.velkomfood.mysap.services.config.InitialConfigurator;
import ru.velkomfood.mysap.services.controller.AnalyticDataEngine;
import ru.velkomfood.mysap.services.controller.ErpDataEngine;
import ru.velkomfood.mysap.services.model.entities.MaterialEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by D. Petrov on 06.06.2016
 * LLC MPP Pavlovskaya Sloboda
 */

@Component
public class TaskRunner {

    @Autowired
    DataSource dataSource;

    // Task scheduler (aka crontab) (second, minute, hour, day, month, weekday)
    @Scheduled(cron = "0 35 13 * * *")
    public void runTaskDataReadingLoading() throws JCoException, SQLException {

        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(InitialConfigurator.class);

        ErpDataEngine erpDataEngine = (ErpDataEngine) ctx.getBean("erpDataEngine");
        erpDataEngine.createErpConnection();

        if (erpDataEngine.testSapConnection()) {

            System.out.println("Connection to SAP is valid");

            Connection h2_conn = dataSource.getConnection();

            AnalyticDataEngine analyticDataEngine = (AnalyticDataEngine) ctx.getBean("analyticDataEngine");
//            erpDataEngine.showSapAttributes();

            try {

                analyticDataEngine.setConnection(h2_conn);

                if (analyticDataEngine.connectionIsValid()) {

                    System.out.println("H2 connection is valid");

                    // Fill tables in the H2 database server (in memory).
                    try {

                        analyticDataEngine.fillMaterialsTable(erpDataEngine.getMaterials());
                        List<MaterialEntity> mtlist = analyticDataEngine.findAllMaterials();

                        final int CAPASITY = mtlist.size();
                        int index = 0;

                        Map<Integer, Boolean> flags = new HashMap<>();
                        flags.put(10, false);
                        flags.put(30, false);
                        flags.put(60, false);
                        flags.put(90, false);
                        flags.put(100, false);

                        System.out.println("Next we must load MRP items");

                        for (MaterialEntity me: mtlist) {

                            index++;

                            analyticDataEngine.fillMrpStockReqList(
                                    erpDataEngine.findMaterialRequirementsPlanning(me));

                            int value = (int) ( ( ((double) index) / ((double) CAPASITY) ) * 100 );

                            switch (value) {
                                case 10:
                                    if (!flags.get(10)) System.out.println("Download is completed for 10 %");
                                    flags.put(10, true);
                                    break;
                                case 30:
                                    if (!flags.get(30)) System.out.println("Download is completed for 30 %");
                                    flags.put(30, true);
                                    break;
                                case 60:
                                    if (!flags.get(60)) System.out.println("Download is completed for 60 %");
                                    flags.put(60, true);
                                    break;
                                case 90:
                                    if (!flags.get(90)) System.out.println("Download is completed for 90 %");
                                    flags.put(90, true);
                                    break;
                                case 100:
                                    if (!flags.get(100)) System.out.println("Download is completed for 100 %");
                                    flags.put(100, true);
                                    break;
                            }

                        }

                        System.out.println("Number MRP items is equal: " + analyticDataEngine.checkNumberItems());

                    } finally {

                        analyticDataEngine.closeConnection();

                    }

                } else {

                    System.out.println("Cannot connect to database");

                }

            } catch (SQLException sqex) {

                System.out.println(sqex.getMessage());

            }


        } else {

            System.out.println("Cannot connect to SAP");

        } //test SAP connection

    } //run the data reading and loading

}
