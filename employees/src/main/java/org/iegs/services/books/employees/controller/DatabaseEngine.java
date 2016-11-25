package org.iegs.services.books.employees.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by petrovdmitry on 13.08.16.
 */
public class DatabaseEngine {

    private static DatabaseEngine instance;
    private String pathTo;
    private final String DBNAME = "employees.db";
    private Connection connection;

    private DatabaseEngine() { }

    public static DatabaseEngine getInstance() {

        if (instance == null) instance = new DatabaseEngine();
        return instance;

    }

    public Connection getConnection() {
        return connection;
    }

    public String getDBNAME() {
        return DBNAME;
    }

    public void setPathTo(String pathTo) {
        this.pathTo = pathTo;
    }

    public void createConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + pathTo + "/" + DBNAME);
    }

}
