package com.softtek.medicine.java.controller;

import com.softtek.medicine.java.util.LoginManager;
import com.softtek.medicine.java.util.MedicineUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controls the login screen
 */
public class LoginController {

    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    public void initialize() {
    }

    public void initManager(final LoginManager loginManager) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sessionID = authorize();
                if (sessionID != null) {
                    loginManager.authenticated(sessionID);
                }
            }
        });
    }

    /**
     * Check authorization credentials.
     *
     * If accepted, return a sessionID for the authorized session otherwise,
     * return null.
     */
    private String authorize() {

        if (MedicineUtil.statusServer()) {
            System.out.println("chamou a baga�a toda");
        }

        return "open".equals(user.getText()) && "sesame".equals(password.getText()) ? generateSessionID() : null;
    }

    private static int sessionID = 0;

    private String generateSessionID() {
        sessionID++;
        return "xyzzy - session " + sessionID;
    }
}