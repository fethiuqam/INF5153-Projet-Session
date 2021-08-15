package com.uqam.controller;

import com.uqam.model.AppException;
import com.uqam.model.Session;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
    private Text errorMessage;

    @FXML
    private HBox errorInterface;

    public void setSession(Session session) {
        this.session = session;
    }


    public static void initialStage(Stage stage, Class cls, Session session) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(cls.getResource("/views/login.fxml"));
        Parent connexionRoot = fxmlLoader.load();
        ConnexionController controller = fxmlLoader.getController();
        controller.setSession(session);
        Scene scene = new Scene(connexionRoot);
        stage.getIcons().add(new Image("/images/windowIcon.png"));
        stage.setTitle("CentRAMQ Accès Médecin - Connexion");
        stage.setScene(scene);
        stage.setResizable(false);
    }

    public void login(javafx.scene.input.MouseEvent mouseEvent) throws IOException, AppException {

        try {
            String usernameInput = username.getText();
            String passwordInput = password.getText();

            Boolean successful = session.login(usernameInput, passwordInput);

            if (successful) {
                Stage mainStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                HomeController.initialStage(mainStage,getClass(),session);
            }
        } catch (AppException e) {

            if (errorInterface.isVisible()) {
                TranslateTransition shake = new TranslateTransition(Duration.millis(40), errorInterface);
                shake.setFromX(0.0);
                shake.setByX(5);
                shake.setCycleCount(3);
                shake.setAutoReverse(true);
                shake.playFromStart();

            } else {
                errorInterface.setVisible(true);
            }
            errorMessage.setText(e.getMessage());
        }
    }
}
