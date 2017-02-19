package org.iegs.info.books.view;

import com.vaadin.ui.*;
import org.iegs.info.books.controller.UserSession;
import org.iegs.info.books.model.User;

/**
 * Created by petrovdmitry on 18.02.17.
 */
public class AccessWindow extends Window {

    private StartUI startWindow;
    private UserSession userSession;

    VerticalLayout layout;
    TextField inputUser;
    PasswordField inputPassword;
    Button btnOk;
    Label accessDeny;

    public AccessWindow(StartUI startWindow, UserSession userSession) {

        super("Авторизация");
        center();

        this.startWindow = startWindow;
        this.userSession = userSession;

        layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setClosable(false);
        setContent(layout);

        inputUser = new TextField("Пользователь: ");
        inputPassword = new PasswordField("Пароль: ");
        btnOk = new Button("Вход");
        btnOk.addClickListener(clickEvent -> pressOkButton());
        accessDeny = new Label("Доступ запрещен");
        accessDeny.setVisible(false);

        layout.addComponents(inputUser, inputPassword, btnOk, accessDeny);

    }

    public void pressOkButton() {

        if (userSession.getUsers().containsKey(inputUser.getValue())) {

            User user = userSession.getUsers().get(inputUser.getValue());

            if (user.getPassword().equals(inputPassword.getValue())) {
                this.close();
            } else {
                accessDeny.setVisible(true);
            }

        }

    }

} // end of this class
