package ru.velkomfood.mysap.mrp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.velkomfood.mysap.mrp.controller.DbStatus;
import ru.velkomfood.mysap.mrp.controller.ErpDataEngine;

/**
 * Created by dpetrov on 19.10.16.
 */

@Configuration
public class MyConfig {

    @Bean
    public ErpDataEngine erpDataEngine() {
        return new ErpDataEngine();
    }

    @Bean
    public DbStatus dbStatus() {
        return new DbStatus();
    }

}
