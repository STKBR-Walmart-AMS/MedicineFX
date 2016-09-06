/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softtek.medicine.java.controller;

/**
 * Sample Skeleton for 'MyMenus.fxml' Controller Class
 */
//import com.softtek.medicine.java.Main;
import com.softtek.medicine.java.MainLogin;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Note that we load the panes with the FXMLLoader on every use. This allows us
 * to manipulate the CSS between loads and have it take affect.
 *
 * Also, the panes should not save state internally. Reloading the FXML forces
 * better programming design, because it is impossible to get lazy and expect
 * the panes to save their own state.
 */
public class MenuController {
    
    @FXML
    private MenuItem menuIndex;
    
    @FXML
    private MenuItem menuItemQuit;
    
    @FXML
    private MenuItem menuItemIncidentManagement;
    
    @FXML
    private MenuItem menuItemIncidentReports;

    /**
     * Event handler for MenuItem one
     */

    @FXML
    private void indexAction(ActionEvent event) {

        try {

            URL paneTwoUrl = getClass().getClassLoader().getResource("com/softtek/medicine/resources/fxml/Index.fxml");
            AnchorPane paneTwo = FXMLLoader.load(paneTwoUrl);

            BorderPane border = MainLogin.getRoot();
            border.setCenter(paneTwo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void quitAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void incidentManagementAction(ActionEvent event) {

        try {

            URL incidentsUrl = getClass().getClassLoader().getResource("com/softtek/medicine/resources/fxml/Incidents.fxml");
            AnchorPane incidents = FXMLLoader.load(incidentsUrl);

            BorderPane border = MainLogin.getRoot();
            border.setCenter(incidents);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
