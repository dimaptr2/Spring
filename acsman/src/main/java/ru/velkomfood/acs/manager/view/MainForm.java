package ru.velkomfood.acs.manager.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import ru.velkomfood.acs.manager.controller.DbManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by velkomfood on 17.02.17.
 */

public class MainForm extends FormLayout {

    DateField fromDate = new DateField("На дату:");
    NativeSelect selector = new NativeSelect("Четланин:");
    Button btnOk = new Button("Выполнить");
    MainUI mainUI;

    @Autowired
    private DbManager dbManager;

    public MainForm(MainUI mainUI) {

        this.mainUI = mainUI;

        Date now = new Date();
        fromDate.setDateFormat("yyyy-MM-dd");
        fromDate.setValue(now);

        Map<String, String> potzs = readAccessFile("./potz.txt");

        // Fill values for the selector
        potzs.forEach((k, v) -> {
            String row = k + " " + v;
            selector.addItem(row);
        });
        selector.setValue("395 Винниченко Павел Михайлович");

        btnOk.addClickListener(clickEvent -> pressOkButton(potzs));

        this.setMargin(true);
        this.setSpacing(true);
        this.addComponents(fromDate, selector, btnOk);

    }

    // Event for Enter key
    public void pressOkButton(Map<String, String> users) {

        dbManager.setUsers(users);
        dbManager.setFromDate(String.valueOf(fromDate.getValue()));

        try {
            dbManager.readEventsByUid();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    // Read a file line by line and build the map with the users
    private static Map<String, String> readAccessFile(String fileName) {

        Map<String, String> uids = new HashMap<>();

        BufferedReader br = null;
        String sCurrentLine;

        try {

            br = new BufferedReader(
                    new FileReader(fileName));

            while ((sCurrentLine = br.readLine()) != null) {
                String[] lines = sCurrentLine.split(" ");
                uids.put(lines[0], lines[1] + " " + lines[2] + " " + lines[3]);
            }

        } catch (IOException ef) {

            ef.printStackTrace();

        } finally {

            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return uids;
    }

}
