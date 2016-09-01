package com.softtek.medicine.java.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.softtek.medicine.java.controller.LoginController;
import com.softtek.medicine.java.controller.MainController;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Manages control flow for logins
 */
public class LoginManager {

    private Scene scene;

    public LoginManager(Scene scene) {
        this.scene = scene;
    }

    /**
     * Callback method invoked to notify that a user has been authenticated.
     * Will show the main application screen.
     */
    public void authenticated(String sessionID) throws IOException {
        //showMain(sessionID);
        showMainNew(sessionID);
    }

    /**
     * Callback method invoked to notify that a user has logged out of the main
     * application. Will show the login application screen.
     */
    public void logout() {
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/softtek/medicine/resources/fxml/Login.fxml"));
            scene.setRoot((Parent) loader.load());
            LoginController controller = loader.<LoginController>getController();
            controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showMain(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/softtek/medicine/resources/fxml/Main.fxml"));
            scene.setRoot((Parent) loader.load());
            MainController controller = loader.<MainController>getController();
            controller.initSessionID(this, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Creating a static root to pass to the controller
    private static BorderPane root = new BorderPane();

    /**
     * Just a root getter for the controller to use
     */
    public static BorderPane getRoot() {
        return root;
    }

    private void showMainNew(String sessionID) throws IOException {

        Stage primaryStage = new Stage();
            
        // loading FXML resources
        URL menuBarUrl = getClass().getClassLoader().getResource("com/softtek/medicine/resources/fxml/Menu.fxml");
        MenuBar bar = FXMLLoader.load(menuBarUrl);

        URL indexUrl = getClass().getClassLoader().getResource("com/softtek/medicine/resources/fxml/Index.fxml");
        AnchorPane index = FXMLLoader.load(indexUrl);

        // constructing our scene using the static root
        root.setTop(bar);
        root.setCenter(index);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
