package ru.bin.bank.info.controller;

import org.springframework.stereotype.Component;
import ru.bin.bank.info.model.Employee;
import ru.bin.bank.info.model.Phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by petrovdmitry on 01.01.17.
 */

@Component
public class DbManager {

    private String url;
    private static DbManager instance;
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

    public void SaveEmployee(Employee e) throws SQLException {

    }

    public void SavePhone(Phone ph) throws SQLException {

    }

    public void DeleteEmployee(Employee e) throws SQLException {

    }

    public void DeletePhone(Phone ph) throws SQLException {

    }

}
