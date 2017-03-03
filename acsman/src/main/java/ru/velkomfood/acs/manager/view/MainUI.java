package ru.velkomfood.acs.manager.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by velkomfood on 17.02.17.
 */
@SpringUI
@Theme("valo")
public class MainUI extends UI {

    private VerticalLayout mainVLayout;
    private HorizontalLayout mainHLayout;
    private AccessWindow accessWindow;
    private MainForm form;

    // Main grid


    @Override
    protected void init(VaadinRequest vaadinRequest) {

        accessWindow = new AccessWindow(this);
        UI.getCurrent().addWindow(accessWindow);

        addMainForm();
        setContent(mainVLayout);

    }

    // PRIVATE SECTION
    private void addMainForm() {

        mainVLayout = new VerticalLayout();
        mainVLayout.setSizeFull();

        mainHLayout = new HorizontalLayout();
        mainHLayout.setSizeFull();
        form = new MainForm(this);
        form.setMargin(true);
        form.setSpacing(true);
        form.setWidth("300px");
        mainHLayout.addComponent(form);

        mainVLayout.addComponent(mainHLayout);

    }

}
