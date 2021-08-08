package com.uqam.main;


import com.uqam.controller.ConnexionController;
import com.uqam.model.Session;
import com.uqam.dao.DataSource;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login.fxml"));

        //create session

        //Session session = new Session(new DataSource()) sinon Session session = new Session(DataSource.getInstance());
        Session session = new Session(new DataSource());

        connectSession(fxmlLoader, session);

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

    public void connectSession (FXMLLoader fxmlLoader, Session session){
        //add session to controller
        ConnexionController controller = fxmlLoader.getController();
        controller.setSession(session);
    }
}
