package ru.bin.bank.info.phones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bin.bank.info.phones.controller.DbManager;

/**
 * Created by on 31.12.2016.
 */

@Configuration
public class MyConfigurator {

    @Bean
    public DbManager dbManager() {
        return new DbManager();
    }

}
