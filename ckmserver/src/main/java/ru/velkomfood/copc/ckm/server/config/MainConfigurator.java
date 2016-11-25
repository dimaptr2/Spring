package ru.velkomfood.copc.ckm.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.velkomfood.copc.ckm.server.controller.SapEngine;

import javax.sql.DataSource;

/**
 * Created by dpetrov on 25.08.2016.
 */

@Configuration
public class MainConfigurator {

    @Bean(name = "h2data")
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.
                setType(EmbeddedDatabaseType.H2).
                setName("ckml").
                build();
        return (DataSource) db;
    }

    @Bean(name = "sap_data_source")
    public SapEngine sapEngine() {
        return new SapEngine();
    }

}
