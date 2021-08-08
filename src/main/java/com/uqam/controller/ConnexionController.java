package com.uqam.controller;

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

    @FXML
    private TextField usrename;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    public void login(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
        var scene = new Scene(newRoot);
        Stage mainStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        mainStage.setScene(scene);
    }
}
