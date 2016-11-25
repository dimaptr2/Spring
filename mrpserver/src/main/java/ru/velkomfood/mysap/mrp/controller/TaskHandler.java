package ru.velkomfood.mysap.mrp.controller;

import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dpetrov on 19.10.16.
 */

@Component
public class TaskHandler {

    @Autowired
    private ErpDataEngine erpDataEngine;
    @Autowired
    private DbStatus dbStatus;

    // second, minute, hour, day, month, weekday
    // Run every 5 minutes
    @Scheduled(cron = "0 9 18 * * *")
    public void retrieveSapData() throws JCoException, SQLException {

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        // month's numbers starts from 0
        month = month + 1;

        String dbName = "jdbc:sqlite:db" + "/" + year + "-" + month + ".db";
        Connection connection = DriverManager.getConnection(dbName);

        long start = new Date().getTime();

        erpDataEngine.setConnection(connection);
        erpDataEngine.setYear(year);
        erpDataEngine.setMonth(month);

        if (erpDataEngine.getjCoDestination() == null) {
            erpDataEngine.initConnection();
        }

        if (dbStatus.isInitial()) {
            erpDataEngine.initDatabase();
        }

        if (!dbStatus.isLoading()) {
            dbStatus.setLoading(true);
            erpDataEngine.refreshDatabase();
            System.out.println("The database was refreshed");
            erpDataEngine.transferData();
            System.out.println("Data was transferred");
            countRows(connection);
            dbStatus.setLoading(false);
        }

        connection.close();
        dbStatus.setInitial(false);

        long finish = new Date().getTime();

        System.out.printf("Time of execution is %d minutes\n", ((finish - start) / 60000));

    }

    // count the number of rows
    private void countRows(Connection conn) throws SQLException {

        Statement stmt = conn.createStatement();

        try {

            int value = retreiveNumberOfRows(stmt, "SELECT count( matnr ) AS value FROM mara");
            System.out.printf("Number of materials is %d\n", value);

            value = retreiveNumberOfRows(stmt, "SELECT count( * ) AS value FROM mbew");
            System.out.printf("Number of valuations is %d\n", value);

            value = retreiveNumberOfRows(stmt, "SELECT count( uom_sap ) AS value FROM units");
            System.out.printf("Number of UOMs is %d\n", value);

            value = retreiveNumberOfRows(stmt, "SELECT count( * ) AS value FROM stocks");
            System.out.printf("Number rows of stocks is %d\n", value);

            value = retreiveNumberOfRows(stmt, "SELECT count( * ) AS value FROM mrpitems");
            System.out.printf("Number rows of MRP items is %d\n", value);

        } finally {

            stmt.close();

        }

    } // count the number of rows

    // Make the request to the database.
    private int retreiveNumberOfRows(Statement rfstmt, String sql) throws SQLException {

        int value = 0;

        ResultSet rs = rfstmt.executeQuery(sql);

        while (rs.next()) {
            value = rs.getInt("value");
        }
        rs.close();

        return value;

    }

}
