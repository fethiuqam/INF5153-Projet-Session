package com.uqam.controller;

import com.uqam.model.AppException;
import com.uqam.model.Doctor;
import com.uqam.model.Session;
import javafx.animation.TranslateTransition;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HomeController {

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

    @FXML
    private Text errorMessage;

    @FXML
    private HBox errorInterface;


    private Session session;

    public void initialize() {

        Platform.runLater(() -> {

            // Doctor information
            Doctor doctor = session.getDoctor();

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

    public void newVisite(MouseEvent mouseEvent) throws IOException, AppException {

        try {
            boolean successful = session.downloadFolder(insuranceSearchQuery.getText());
            if (successful){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/patient.fxml"));
                Parent patientRoot = (Parent) fxmlLoader.load();

                connectSession(fxmlLoader, session);

                var scene = new Scene(patientRoot);
                Stage mainStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                mainStage.setScene(scene);
            }
        }catch (AppException e){

            if (errorInterface.isVisible()){
                TranslateTransition shake = new TranslateTransition(Duration.millis(40), errorInterface);
                shake.setFromX(0.0);
                shake.setByX(5);
                shake.setCycleCount(3);
                shake.setAutoReverse(true);
                shake.playFromStart();

            }else{
                errorInterface.setVisible(true);
            }
            errorMessage.setText(e.getMessage());
        }





    }

    public void setSession (Session session){
        this.session = session;
    }

    public void connectSession (FXMLLoader fxmlLoader, Session session){
        //add session to controller
        FolderController controller = fxmlLoader.getController();
        controller.setSession(session);
    }
}
