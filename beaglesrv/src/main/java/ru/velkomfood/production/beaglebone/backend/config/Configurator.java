package ru.velkomfood.production.beaglebone.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.velkomfood.production.beaglebone.backend.controller.SQLiteManager;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by dpetrov on 16.06.16.
 */

@Configuration
public class Configurator {

    // SQLite DB connection
    @Bean
    public Connection connection() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");

        String path = "/opt/cachebeagle/beagledb.db";

        return DriverManager.getConnection("jdbc:sqlite:" + path);

    }

    @Bean
    public SQLiteManager sqLiteManager() {
        return new SQLiteManager();
    }

}
