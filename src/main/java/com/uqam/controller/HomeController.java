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
import javafx.scene.image.Image;
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
    private AnchorPane scannerActivated;

    @FXML
    private TextField insuranceSearchQuery;

    @FXML
    private Text errorMessage;

    @FXML
    private HBox errorInterface;


    private Session session;

    public void setSession (Session session){
        this.session = session;
    }

    public static void initialStage(Stage stage , Class cls , Session session) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(cls.getResource("/views/home.fxml"));
        Parent homeRoot = fxmlLoader.load();
        HomeController controller = fxmlLoader.getController();
        controller.setSession(session);
        Scene scene = new Scene(homeRoot);
        stage.getIcons().add(new Image("/images/windowIcon.png"));
        stage.setTitle("CentRAMQ Accès Médecin - Accueil");
        stage.setScene(scene);
        stage.setResizable(false);
    }

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

        Stage mainStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        ConnexionController.initialStage(mainStage, getClass(),session);
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
                Stage mainStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                FolderController.initialStage(mainStage, getClass(), session);
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

}
