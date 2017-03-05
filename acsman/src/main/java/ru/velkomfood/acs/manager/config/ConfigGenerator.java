package ru.velkomfood.acs.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.velkomfood.acs.manager.controller.DbManager;

/**
 * Created by velkomfood on 17.02.17.
 */
@Configuration
public class ConfigGenerator {

//    private final String URL = "jdbc:firebirdsql://srv-acs2.eatmeat.ru:3053/" +
//            "E:/BASE/OKO.FDB?user=SYSDBA&password=masterkey";
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dsm = new DriverManagerDataSource();
//        dsm.setUrl(URL);
//        return dsm;
//    }

    @Bean
    public DbManager dbManager() {
        return new DbManager();
    }

}
