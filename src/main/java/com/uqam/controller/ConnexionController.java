package com.uqam.controller;

import com.uqam.model.AppException;
import com.uqam.model.Session;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class ConnexionController {

    Session session;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Text errorMessage;

    @FXML
    private HBox errorInterface;

    public void login(javafx.scene.input.MouseEvent mouseEvent) throws IOException , AppException {

        try{
            String usernameInput = username.getText();
            String passwordInput = password.getText();

            Boolean successful = session.login(usernameInput,passwordInput);

            if (successful) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
                Parent homeRoot = (Parent) fxmlLoader.load();

                connectSession(fxmlLoader, session);

                var scene = new Scene(homeRoot);
                Stage mainStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                mainStage.setTitle("CentRAMQ Accès Médecin - Accueil");
                mainStage.setScene(scene);
            }
        } catch (AppException e){

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
        HomeController controller = fxmlLoader.getController();
        controller.setSession(session);
    }
}
