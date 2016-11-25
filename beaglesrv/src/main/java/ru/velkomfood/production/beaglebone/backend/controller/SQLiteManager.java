package ru.velkomfood.production.beaglebone.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.velkomfood.production.beaglebone.backend.model.EventsEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dpetrov on 17.06.16.
 */

public class SQLiteManager {

    @Autowired
    private Connection conndb;

    public List<EventsEntity> readDatabase() throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;

        List<EventsEntity> list = new ArrayList<>();

        try {

            stmt = conndb.createStatement();
            String sql = "SELECT year, month, day, count( * ) AS nrows FROM events" +
                    " GROUP BY year, month, day";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                EventsEntity en = new EventsEntity();
                en.setYear(rs.getInt("year"));
                en.setMonth(rs.getString("month"));
                en.setDay(rs.getInt("day"));
                en.setNumberRows(rs.getLong("nrows"));
                list.add(en);
            }

        } finally {

            rs.close();
            stmt.close();

        }

        return list;

    }


    public void closeConnection() throws SQLException {
        if (!conndb.isClosed()) conndb.close();
    }
}
