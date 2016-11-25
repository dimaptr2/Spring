package ru.velkomfood.production.beaglebone.infosys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.velkomfood.production.beaglebone.infosys.controller.BeagleBoneInfo;
import ru.velkomfood.production.beaglebone.infosys.controller.BeagleBoneTester;
import ru.velkomfood.production.beaglebone.infosys.controller.MailSender;

/**
 * Created by dpetrov on 10.06.16.
 */

@Configuration
public class InitialConfigurator {

    @Bean
    public BeagleBoneTester beagleBoneTester() {
        return new BeagleBoneTester();
    }

    @Bean
    public MailSender mailSender() {
        return new MailSender();
    }

    @Bean
    public BeagleBoneInfo beagleBoneInfo() {
        return new BeagleBoneInfo();
    }

}
