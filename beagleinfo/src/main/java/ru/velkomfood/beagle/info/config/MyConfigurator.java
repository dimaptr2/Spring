package ru.velkomfood.beagle.info.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by dpetrov on 21.04.17.
 */

@Component
public class MyConfigurator {

    @Bean
    public DataSource dataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("srv-sapapp.eatmeat.ru");
        ds.setPort(3306);
        ds.setDatabaseName("beagle");
        ds.setUser("beagle");
        ds.setPassword("1qaz@WSX");
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
