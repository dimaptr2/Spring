package ru.velkomfood.acs.inquisitors.tasks;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.velkomfood.acs.inquisitors.config.MyConfigurator;
import ru.velkomfood.acs.inquisitors.controller.OurDataManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by Velkomfood on 27.06.16.
 */

@Component
public class MainTaskHandler extends Thread {

    // Execute this every minute
    @Scheduled(cron = "0 */15 *  * * *") // second, minute, hour, day, month, day week
    public void runCheckInputs() throws SQLException, IOException, ParseException, InterruptedException {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfigurator.class);

        OurDataManager ourDataManager = (OurDataManager) ctx.getBean("ourDataManager");
        DataSource ds = ourDataManager.getDataSource();

        if (ds != null) {

            ourDataManager.readUidFromFile();
            ourDataManager.buildUsersEntities();
            ourDataManager.readEvents();

        } else {

            throw new SQLException("Data source not found");

        }

    } //end of method

}
