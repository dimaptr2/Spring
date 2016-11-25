package ru.velkomfood.mysap.services.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.velkomfood.mysap.services.controller.AnalyticDataEngine;
import ru.velkomfood.mysap.services.controller.DiggerOfData;
import ru.velkomfood.mysap.services.controller.ErpDataEngine;
import ru.velkomfood.mysap.services.model.templates.MaterialJdbcTemplate;
import ru.velkomfood.mysap.services.model.templates.MaterialSumJdbcTemplate;
import ru.velkomfood.mysap.services.model.templates.MrpRequestJdbcTemplate;
import ru.velkomfood.mysap.services.model.templates.PurchaseGroupJdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by dpetrov on 06.06.16.
 */

@Configuration
public class InitialConfigurator {

    @Bean
    public ErpDataEngine erpDataEngine() {
        return new ErpDataEngine();
    }

    @Bean
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
                                    .setName("sapdata")
                                    .build();
        return db;
    }

    @Bean
    public AnalyticDataEngine analyticDataEngine() {
        return new AnalyticDataEngine();
    }

    @Bean(name = "mrpJdbc")
    public MrpRequestJdbcTemplate mrpRequestJdbcTemplate() {
        return new MrpRequestJdbcTemplate();
    }

    @Bean
    public MaterialJdbcTemplate materials() {
        return new MaterialJdbcTemplate();
    }

    @Bean
    public PurchaseGroupJdbcTemplate purchaseGroups() {
        return new PurchaseGroupJdbcTemplate();
    }

    @Bean
    public MaterialSumJdbcTemplate sumByMaterial() {
        return new MaterialSumJdbcTemplate();
    }

    @Bean
    public DiggerOfData digger() {
        return new DiggerOfData();
    }

}
