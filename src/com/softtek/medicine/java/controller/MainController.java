package com.softtek.medicine.java.controller;

import com.softtek.medicine.java.util.LoginManager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/** Controls the main application screen */
public class MainController {
	private Button logoutButton;
	private Label sessionLabel;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem menuItemQuit;
    @FXML
    private MenuItem menuItemIncidentManagement;
    @FXML
    private MenuItem menuItemIncidentReports;
    @FXML
    private Pane centralPane;
    @FXML
    private Label leftStatus;
    @FXML
    private Color x4;
    @FXML
    private Font x3;
    @FXML
    private Pane bottomPane;
    @FXML
    private Label rightStatus;

	public void initialize() {
	}

	public void initSessionID(final LoginManager loginManager, String sessionID) {
		sessionLabel.setText(sessionID);
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loginManager.logout();
			}
		});
	}
}