package ru.bin.bank.info.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bin.bank.info.controller.DbManager;
import ru.bin.bank.info.model.Employee;

/**
 * Created by petrovdmitry on 05.01.17.
 */
@SpringComponent
@UIScope
public class StartController {

    @Autowired
    private DbManager db;

    public void saveEmployee(Employee e) {

    }

}
