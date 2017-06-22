package ru.velkomfood.fin.cash.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by dpetrov on 22.06.17.
 */
@Component
public class DBEngine {

    private boolean initial;
    @Autowired
    private DataSource dataSource;


    @PostConstruct
    public void initStatus() throws SQLException {
        initial = true;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

}
