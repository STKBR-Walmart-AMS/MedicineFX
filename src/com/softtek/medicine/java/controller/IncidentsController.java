/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softtek.medicine.java.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.softtek.medicine.java.model.Incident;
import com.softtek.medicine.java.util.MedicineRestClientUtil;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author alam.izaguirre
 */
public class IncidentsController implements Initializable {

    @FXML
    private TableView<Incident> IncidentTableView;
    @FXML
    private TableColumn statusCol;
    @FXML
    private TableColumn submitDateCol;
    @FXML
    private TableColumn priorityCol;
    @FXML
    private TableColumn incidentNumberCol;
    @FXML
    private TableColumn descriptionCol;

    // The table's data
    ObservableList<Incident> data;

    MedicineRestClientUtil restClientUtil = new MedicineRestClientUtil();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Set up the table data
        statusCol.setCellValueFactory(new PropertyValueFactory<Incident, Integer>("status"));
        submitDateCol.setCellValueFactory( new PropertyValueFactory<Incident, Date>("submitDate"));
        priorityCol.setCellValueFactory( new PropertyValueFactory<Incident, Integer>("priority"));
        incidentNumberCol.setCellValueFactory( new PropertyValueFactory<Incident, String>("incidentNumber"));
        descriptionCol.setCellValueFactory( new PropertyValueFactory<Incident, String>("description"));
        
        try {
            // retrieve list of Incidents
            data = FXCollections.observableArrayList(restClientUtil.getAllIncidents());
        } catch (ParseException ex) {
            Logger.getLogger(IncidentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // set Incidents list to TableView
        IncidentTableView.setItems(data);

    }

}
