package ru.velkomfood.sap.tv.server4.periodic;

import com.sap.conn.jco.JCoException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.velkomfood.sap.tv.server4.controller.DbProcessor;
import ru.velkomfood.sap.tv.server4.controller.ErpReader;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class PeriodicDataProcessing {

    private DbProcessor dbProcessor;
    private ErpReader erpReader;
    private Logger logger;

    @Autowired
    public PeriodicDataProcessing(DbProcessor dbProcessor, ErpReader erpReader) {
        this.dbProcessor = dbProcessor;
        this.erpReader = erpReader;
    }

    @Autowired
    @Qualifier("mainLogger")
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    // Run periodically (seconds, minutes, hours, day, month, day of week)
    @Scheduled(cron = "0 */1 * * * *")
    public void uploadDataFromErp() {

        LocalDateTime ts1 = LocalDateTime.now();
        LocalDate currentDate = LocalDate.now();
        // Delete all rows older than 8 days
//        dbProcessor.refreshDataBeforeDate(java.sql.Date.valueOf(currentDate.minusDays(8)));
        LocalDate[] rangeDates = buildRangeDates(currentDate);

        try {
            for (LocalDate date : rangeDates) {
                erpReader.takeOutgoingDeliveriesInfo(date);
            }
            logger.info("The data uploading is finished");
        } catch (JCoException ex) {
            logger.error(ex.getMessage());
        }

        LocalDateTime ts2 = LocalDateTime.now();
        logger.info(String.format("Time of execution is %d seconds", (ts2.getSecond() - ts1.getSecond())));

    }

    private LocalDate[] buildRangeDates(LocalDate moment) {

        LocalDate[] range = new LocalDate[4];

        range[0] = moment.minusDays(2);
        range[1] = moment.minusDays(1);
        range[2] = moment;
        range[3] = moment.plusDays(1);

        return range;
    }

}
