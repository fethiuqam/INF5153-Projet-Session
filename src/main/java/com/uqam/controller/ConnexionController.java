package com.uqam.controller;

import com.uqam.main.MyApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class ConnexionController {

    Session session;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    public void login(javafx.scene.input.MouseEvent mouseEvent) throws IOException {

        String usernameInput = username.getText();
        String passwordInput = password.getText();

        Boolean successful = session.login(usernameInput,passwordInput);

        if (successful) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Parent homeRoot = (Parent) fxmlLoader.load();

            connectSession(fxmlLoader, session);

            var scene = new Scene(homeRoot);
            Stage mainStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            mainStage.setScene(scene);
        }

    }

    public void setSession (Session session){
        this.session = session;
    }

    public void connectSession (FXMLLoader fxmlLoader, Session session){
        //add session to controller
        HomeController controller = fxmlLoader.getController();
        controller.setSession(session);
    }
}
