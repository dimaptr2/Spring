package ru.velkomfood.production.beaglebone.infosys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.velkomfood.production.beaglebone.infosys.controller.BeagleBoneTester;
import ru.velkomfood.production.beaglebone.infosys.controller.MailSender;

import java.io.IOException;

/**
 * Created by dpetrov on 10.06.16.
 */

@Component
public class TaskHandler {

    @Autowired
    BeagleBoneTester beagleBoneTester;

    @Autowired
    MailSender mailSender;

    // Run the task every 30 seconds
    @Scheduled(cron="*/30 * * * * *") // second, minute, hour, day, month, weekday
    public void runTaskBeagleReading() throws IOException {

        if (beagleBoneTester.checkIpAddress()) {

            if (beagleBoneTester.checkHttpRequest()) {

            }

        }

    }  //end of method

}
