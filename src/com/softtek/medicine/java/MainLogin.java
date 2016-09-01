package com.softtek.medicine.java;

import java.io.IOException;

import com.softtek.medicine.java.util.LoginManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainLogin extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(new StackPane());

        LoginManager loginManager = new LoginManager(this.scene);
        loginManager.showLoginScreen();

        stage.setScene(this.scene);
        stage.show();
    }
}