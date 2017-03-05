package ru.velkomfood.acs.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by velkomfood on 17.02.17.
 */
public class DbManager {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Map<String, String> users;
    private String fromDate;

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void readEventsByUid() throws SQLException {

        int uid;

        for (String key: users.keySet()) {
            uid = Integer.parseInt(key);
        }


    }

}
