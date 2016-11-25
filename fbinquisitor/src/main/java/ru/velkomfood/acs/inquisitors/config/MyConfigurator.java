package ru.velkomfood.acs.inquisitors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.velkomfood.acs.inquisitors.controller.OurDataManager;

import javax.sql.DataSource;

/**
 * Created by Velkomfood on 27.06.16.
 */

@Configuration
public class MyConfigurator {

    private final String URL = "jdbc:firebirdsql://srv-acs2.eatmeat.ru:3053/" +
            "E:/BASE/OKO.FDB?user=SYSDBA&password=masterkey";

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(URL);

        return ds;
    }

    @Bean
    public OurDataManager ourDataManager() {
        return new OurDataManager();
    }

}
