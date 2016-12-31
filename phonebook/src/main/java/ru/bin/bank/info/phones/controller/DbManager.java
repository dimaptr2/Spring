package ru.bin.bank.info.phones.controller;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Петров Д Г on 31.12.2016.
 */
public class DbManager {

    private Connection connection;

    // Open a connection to the SQLite database file.
    public void initDatabaseConnection(String dbpath) throws SQLException {

        java.util.Date now = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String[] dates = fmt.format(now).split("-");

        StringBuilder sb = new StringBuilder(0);
        sb.append("jdbc:sqlite:");
        sb.append(dbpath).append("/phones-").append(dates[0]).append(".db");

        connection = DriverManager.getConnection(sb.toString());

    }
    // Make sure that tables exist
    public boolean isNotTablesExist() throws SQLException {

        boolean flag;
        Statement stmt = connection.createStatement();

        try {
            String sql = "SELECT COUNT( id ) AS enumb FROM employees";
            ResultSet rs = stmt.executeQuery(sql);
            int counter = 0;
            while (rs.next()) {
                counter = rs.getInt("enumb");
            }
            rs.close();
            if (counter > 0) {
                flag = false;
            } else {
                flag = true;
            }
        } finally {
            stmt.close();
        }

        return flag;
    }

    public void createDatabaseTables() throws SQLException {

        String[] commands = buildCreatingTablesStatement();
        Statement stmt = connection.createStatement();

        try {
            for (String line: commands) {
                stmt.addBatch(line);
            }
            stmt.executeBatch();
        } finally {
            stmt.close();
        }

    }

    // Close a connection to the database
    public void closeDbConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private String[] buildCreatingTablesStatement() {

        String[] sql = new String[2];
        return sql;
    }

}
