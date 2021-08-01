package com.uqam.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {


    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane scanCard;

    @FXML
    private Button scanButton;

    @FXML
    private AnchorPane scannerActivated;

    @FXML
    private Button cancelScanButton;

    @FXML
    private AnchorPane keyboardCard;

    @FXML
    private Button searchButton;


    public void logout(MouseEvent mouseEvent) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/views/ConnexionVue.fxml"));
        var scene = new Scene(newRoot);
        Stage mainStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        mainStage.setScene(scene);
    }

    public void showHideScanCard(MouseEvent mouseEvent) {
        if (scannerActivated.isVisible()){
            scannerActivated.setVisible(false);
        }else {
            scannerActivated.setVisible(true);
        }
    }

    public void newVisite(MouseEvent mouseEvent) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/views/patientView.fxml"));
        var scene = new Scene(newRoot);
        Stage mainStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        mainStage.setScene(scene);
    }
}
