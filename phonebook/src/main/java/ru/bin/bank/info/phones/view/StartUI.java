package ru.bin.bank.info.phones.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bin.bank.info.phones.controller.DbManager;
import java.sql.SQLException;

/**
 * Created by Петров Д Г on 31.12.2016.
 */
@SpringUI
@Theme("valo")
public class StartUI extends UI {

    // C:/Users/Петров Д Г/Documents/mydb
    private final String DBPATH = "C:/Users/Петров Д Г/Documents/mydb";
    @Autowired
    private DbManager dbManager;
    private HorizontalLayout hLayout;
    private VerticalLayout vLayoutCommands, vLayoutScene;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        try {
            dbManager.initDatabaseConnection(DBPATH);
            if (dbManager.isNotTablesExist()) {
                dbManager.createDatabaseTables();
            }
            addHorizontalLayout();
            addVerticalLayout();
            dbManager.closeDbConnection();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }

    }

    private void addHorizontalLayout() {

    }

    private void addVerticalLayout() {

    }

}
