package org.iegs.info.books.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.iegs.info.books.controller.DbManager;
import org.iegs.info.books.controller.UserSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by petrovdmitry on 18.02.17.
 */
@SpringUI
@Theme("valo")
public class StartUI extends UI {

    @Autowired
    private DbManager dbManager;
    @Autowired
    private UserSession userSession;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        userSession.initAdminLogin();
        userSession.getAllUsers();

    }

}
