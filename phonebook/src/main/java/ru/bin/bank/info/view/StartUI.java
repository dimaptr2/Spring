package ru.bin.bank.info.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bin.bank.info.controller.DbManager;


/**
 * Created by petrovdmitry on 01.01.17.
 */

@SpringUI
@Theme("valo")
public class StartUI extends UI {

    @Autowired
    private DbManager db;

    private HorizontalLayout hLayoutMain;
    private VerticalLayout vLayoutLeft;
    private VerticalLayout vLayoutRight;
    private FormLayout primaryForm;
    private FormLayout secondaryForm;
    // Widgets
    private TextField firstName;
    private TextField lastName;
    private TextField middleName;
    private TextField phoneNumber;
    private TextField extension;
    private Button btnSave;
    private Button btnPhoneSave;
    private Button btnDelete;
    private Button btnPhoneDelete;

    private String message;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        addFormLayout();
        addVerticalLayout();
        addHorizontalLayout();
        setContent(hLayoutMain);

    }

    private void addFormLayout() {

        firstName = new TextField("Имя");
        lastName = new TextField("Фамилия");
        middleName = new TextField("Отчество");
        btnSave = new Button("Создать");
        btnDelete = new Button("Удалить");
        primaryForm = new FormLayout();
        primaryForm.addComponents(firstName, lastName, middleName, btnSave, btnDelete);
        primaryForm.setCaption("Сотрудники");
        primaryForm.setSpacing(true);
        primaryForm.setMargin(true);
        primaryForm.setComponentAlignment(firstName, Alignment.TOP_RIGHT);
        primaryForm.setComponentAlignment(lastName, Alignment.TOP_RIGHT);
        primaryForm.setComponentAlignment(middleName, Alignment.TOP_RIGHT);
        primaryForm.setComponentAlignment(btnSave, Alignment.TOP_LEFT);
        primaryForm.setComponentAlignment(btnDelete, Alignment.TOP_LEFT);


        phoneNumber = new TextField("Телефон");
        extension = new TextField("Доб. ");
        btnPhoneSave = new Button("Создать");
        btnPhoneDelete = new Button("Удалить");
        secondaryForm = new FormLayout();
        secondaryForm.addComponents(phoneNumber, extension, btnPhoneSave, btnPhoneDelete);
        secondaryForm.setCaption("Телефонные номера");
        secondaryForm.setSpacing(true);
        secondaryForm.setMargin(true);
        secondaryForm.setComponentAlignment(phoneNumber, Alignment.TOP_RIGHT);
        secondaryForm.setComponentAlignment(extension, Alignment.TOP_RIGHT);
        secondaryForm.setComponentAlignment(btnPhoneSave, Alignment.TOP_LEFT);
        secondaryForm.setComponentAlignment(btnPhoneDelete, Alignment.TOP_LEFT);

    }

    private void addVerticalLayout() {
        vLayoutLeft = new VerticalLayout();
        vLayoutLeft.addComponents(primaryForm, secondaryForm);
        vLayoutRight = new VerticalLayout();

    }

    private void addHorizontalLayout() {
        hLayoutMain = new HorizontalLayout();
        hLayoutMain.addComponents(vLayoutLeft, vLayoutRight);
    }

}
