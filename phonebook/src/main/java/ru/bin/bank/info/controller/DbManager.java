package ru.bin.bank.info.controller;

import org.springframework.stereotype.Component;
import ru.bin.bank.info.model.TypeOfEntity;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by petrovdmitry on 01.01.17.
 */

@Component
public class DbManager {

    private String url;
    private Connection connection;

    public DbManager() {

        java.util.Date now = new java.util.Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String[] dt = fmt.format(now).split("-");
        url = "jdbc:sqlite:~/mydb/phones-" + dt[0] + ".db";

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    // Open and Close the database connection
    public void openDatabaseConnection() throws SQLException {
        connection = DriverManager.getConnection(url);
    }

    public void closeDatabaseConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public <T> void create(T entity, TypeOfEntity te) throws SQLException {

    }

    public <T> T read(Long id, TypeOfEntity te) throws SQLException {
        T entity = null;
        return entity;
    }

    public <T> List<T> readAll(Long low, Long high, TypeOfEntity te) throws SQLException {

        List<T> collection = null;

        return collection;

    }

    public <T> void update(T entity, TypeOfEntity te) throws SQLException {

    }

    public <T> void delete(T entity, TypeOfEntity te) throws SQLException {

    }

    public void deleteAll(Long low, Long high, TypeOfEntity te) throws SQLException {

        String sql = "";
        Statement stmt = connection.createStatement();

        try {

        } finally {
            stmt.close();
        }

    }

}
