package com.uqam.controller;

import com.uqam.main.*;
import com.uqam.model.Doctor;
import com.uqam.model.User;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    Session session;

    // ***** User information
    @FXML
    private Label lastName;

    @FXML
    private Label firstName;

    @FXML
    private Label permitNumber;
    // ***** User information

    @FXML
    private HBox logoutButton;

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

    @FXML
    private TextField insuranceSearchQuery;

    public void initialize() {

        Platform.runLater(() -> {

            // Doctor information
            User user = session.getUser();
            Doctor doctor = user.getDoctor();

            firstName.setText(doctor.getFirstname());
            lastName.setText(doctor.getLastname());
            permitNumber.setText(doctor.getPermit());


        });



    }


    public void logout(MouseEvent mouseEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Parent loginRoot = (Parent) fxmlLoader.load();

        ConnexionController controller = fxmlLoader.getController();
        controller.setSession(session);

        var scene = new Scene(loginRoot);
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

        boolean successful = session.downloadFolder(insuranceSearchQuery.getText());
        if (successful){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/patient.fxml"));
            Parent patientRoot = (Parent) fxmlLoader.load();

            connectSession(fxmlLoader, session);

            var scene = new Scene(patientRoot);
            Stage mainStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            mainStage.setScene(scene);
        }




    }

    public void setSession (Session session){
        this.session = session;
    }

    public void connectSession (FXMLLoader fxmlLoader, Session session){
        //add session to controller
        PatientController controller = fxmlLoader.getController();
        controller.setSession(session);
    }
}
