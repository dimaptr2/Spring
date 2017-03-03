package org.iegs.info.books.controller;

import org.iegs.info.books.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by petrovdmitry on 18.02.17.
 */
@Component
public class UserSession {

    private final String FILE_PATH = "./access.properties";
    private Properties properties;
    private Map<String, User> users;
    @Autowired
    private DbManager dbManager;

    public UserSession() {
        properties = new Properties();
        users = new ConcurrentHashMap<>();
    }

    public void initAdminLogin() {

        InputStream input = null;

        try {

            input = new FileInputStream(FILE_PATH);
            properties.load(input);
            User usr = new User(new Long(0), "admin", properties.getProperty("password"));
            users.put("admin", usr);

        } catch (IOException fex) {

            fex.printStackTrace();

        } finally {

            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }

    }

    public void getAllUsers() {

    }

    // add / remove methods
    public void addUser(User user) {
        users.put(user.getName(), user);
    }

    public void removeUser(User user) {
        users.remove(user.getName());
    }

    // setters and getters
    public Map<String, User> getUsers() {
        return users;
    }

}
