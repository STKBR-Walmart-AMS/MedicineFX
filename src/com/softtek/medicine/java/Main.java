package com.softtek.medicine.java;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    // Creating a static root to pass to the controller
    private static BorderPane root = new BorderPane();
    
    private Scene scene;

    /**
     * Just a root getter for the controller to use
     */
    public static BorderPane getRoot() {
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        
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

    public static void main(String[] args) {
        launch(args);
    }
}