package com.uqam.main;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MyApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent connexionRoot = FXMLLoader.load(getClass().getResource("/views/ConnexionVue.fxml"));
        var scene = new Scene(connexionRoot);
        stage.getIcons().add(new Image("/images/windowIcon.png"));
        stage.setTitle("CentRAMQ Accès Médecin");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
