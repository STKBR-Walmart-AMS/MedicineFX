package com.softtek.medicine.java.controller;

import com.softtek.medicine.java.model.Incident;
import com.softtek.medicine.java.util.LoginManager;
import com.softtek.medicine.java.util.MedicineUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.web.client.RestTemplate;

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
    
    public static final String SERVER_URI = "http://medicineweb-stkbr.rhcloud.com/";

    public void initialize() {
    }

    public void initManager(final LoginManager loginManager) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sessionID = authorize();
                if (sessionID != null) {
                    try {
                        loginManager.authenticated(sessionID);
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

//        if (MedicineUtil.statusServer()) {
//            System.out.println("chamou a bagaça toda");
//        }
        RestTemplate restTemplateTest = new RestTemplate();
        List<LinkedHashMap> emps = restTemplateTest.getForObject(SERVER_URI + "/dr/incidents/request", List.class);
        System.out.println(emps.size());

        MedicineUtil util = new MedicineUtil();
        List<Incident> incidentList = new ArrayList<>();
        incidentList = util.recoverIncidents(user.getText(), password.getText(), null);
        if (!incidentList.isEmpty()) {
            RestTemplate restTemplate = new RestTemplate();
            //System.out.println("recover incidents\n");
            String result = restTemplate.postForObject(SERVER_URI + "/dr/incidents/update", incidentList, String.class);
            System.out.println(result);
        }
  
        return "open".equals(user.getText()) && "sesame".equals(password.getText()) ? generateSessionID() : null;
    }

    private static int sessionID = 0;

    private String generateSessionID() {
        sessionID++;
        return "xyzzy - session " + sessionID;
    }
}
