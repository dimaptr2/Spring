package ru.velkomfood.fin.cash.server.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MyConfigurator {

    @Bean
    @Qualifier("myDataSource")
    public DataSource myDataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("");
        ds.setDatabaseName("");
        ds.setPort(3306);
        ds.setUser("");
        ds.setPassword("");
        return ds;
    }

}
