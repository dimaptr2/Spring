package ru.velkomfood.production.beaglebone.infosys.controller;

import ru.velkomfood.production.beaglebone.infosys.model.EventsEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dpetrov on 11.06.16.
 */

public class BeagleBoneInfo {

    private String path;
    private final String DB_FILE = "/beaglebone/beagledb.db";
    private Connection connection;
    private Statement statement;

    public BeagleBoneInfo() {

        path = System.getProperty("user.dir");
        path.replace("\\\\", "/");
        path += DB_FILE;

    }

    public void initDatabaseConnection() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");

        String connUrl = "jdbc:sqlite:" + path;

        connection = DriverManager.getConnection(connUrl);

        if (connection != null)
            statement = connection.createStatement();

    }

    public List<EventsEntity> executeQuery() throws SQLException {

        EventsEntity dbrow;
        List<EventsEntity> rowCollection;

        rowCollection = new ArrayList<>();

        return rowCollection;

    }

    public void closeConnection() throws SQLException {

        if (!connection.isClosed() || connection != null) {
            connection.close();
        }

    }
}
