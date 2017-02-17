package ru.velkomfood.acs.manager.view;

import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by velkomfood on 17.02.17.
 */
public class AccessWindow extends Window {

    private final String PASSWORD = "1qaz@WSX";
    private MainUI mainUI;

    VerticalLayout layout;
    TextField inputUser;
    PasswordField inputPassword;
    Button btnPermit;
    Label message;

    public AccessWindow(MainUI mainUI) {

        super("Access Control");
        this.mainUI = mainUI;
        center();
        this.setModal(true);
        this.setClosable(false);

        layout = new VerticalLayout();
        inputUser = new TextField("User: ");
        inputPassword = new PasswordField("Password: ");
        btnPermit = new Button("Ok");
        message = new Label();
        message.setVisible(false);

        layout.setMargin(true);
        layout.setSpacing(true);
        btnPermit.addClickListener(clickEvent -> checkEnterCode());
        layout.addComponents(inputUser, inputPassword, btnPermit, message);

        setContent(layout);

    }

    // Access
    public void checkEnterCode() {
        if (isPermitted()) {
            this.close();
        } else {
            message.setVisible(true);
            message.setValue("Access denied");
        }
    }

    // Only for user = acsman
    private boolean isPermitted() {

        boolean flag = false;

        Map<String, String> access = new HashMap<>();

        access.put("user", inputUser.getValue());
        access.put("password", inputPassword.getValue());

        if (access.get("user").equals("acsman")
                && access.get("password").equals(PASSWORD)) {
            flag = true;
        }

        return flag;
    }

}
