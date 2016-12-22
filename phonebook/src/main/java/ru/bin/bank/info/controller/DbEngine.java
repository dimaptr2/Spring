package ru.bin.bank.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;


/**
 * Created by dpetrov on 21.12.16.
 */
@Component
public class DbEngine {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void initDatabaseStatus() throws SQLException {

        StringBuilder sb = new StringBuilder(0);

        sb.append("CREATE TABLE IF NOT EXISTS employees (id INTEGER PRIMARY KEY, first_name VARCHAR(50),");
        sb.append(" last_name VARCHAR(50), middle_name VARCHAR(50))");
        jdbcTemplate.execute(sb.toString());
        sb.delete(0, sb.length());

        sb.append("CREATE TABLE IF NOT EXISTS phones (id INTEGER PRIMARY KEY");
        sb.append(" , pnumber VARCHAR(18), extension VARCHAR(5), employee_id INTEGER NOT NULL,");
        sb.append(" FOREIGN KEY (employee_id) REFERENCES employees(id))");
        jdbcTemplate.execute(sb.toString());
        sb.delete(0, sb.length());

    }


}
