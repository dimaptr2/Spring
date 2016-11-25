package org.iegs.services.books.phones.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Created by petrovdmitry on 11.09.16.
 */
@Configuration
public class BasicConfigurator {

    @Bean
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).
                setName("phonebook").build();

        return db;
    }
}
