package com.uqam.main;

import com.uqam.controller.ConnexionController;
import com.uqam.dao.DataSource;
import com.uqam.model.Session;
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
        Session session = new Session(new DataSource());
        ConnexionController.initialStage(stage, getClass(),session);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
