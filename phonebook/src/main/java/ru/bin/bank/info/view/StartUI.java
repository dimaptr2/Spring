package ru.bin.bank.info.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bin.bank.info.controller.DbEngine;

import java.sql.SQLException;


/**
 * Created by dpetrov on 21.12.16.
 */
@SpringUI
@Theme("valo")
public class StartUI extends UI {

    @Autowired
    private DbEngine dbe;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        String errorMessage;

        try {
            dbe.initDatabaseStatus();

        } catch (SQLException sqe) {
            errorMessage = sqe.getMessage();
        }

    } // init-method

}
