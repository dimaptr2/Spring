package org.iegs.services.books.employees.config;

import org.iegs.services.books.employees.controller.DatabaseEngine;

/**
 * Created by petrovdmitry on 13.08.16.
 */
public class MyConfigurator {

    private static MyConfigurator instance;


    private MyConfigurator() { }

    public static MyConfigurator getInstance() {

        if (instance == null) instance = new MyConfigurator();

        return instance;

    }


    public DatabaseEngine databaseEngine() {
        return DatabaseEngine.getInstance();
    }

}
