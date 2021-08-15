package com.uqam.controller;

import com.uqam.dao.DataSource;
import com.uqam.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Init;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.control.LabeledMatchers;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.matcher.control.TextMatchers;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import static org.mockito.Mockito.when;


@ExtendWith({ApplicationExtension.class, MockitoExtension.class})
public class ConnexionControllerTest {

    @Mock
    DataSource dataSourceMock;
    Doctor doctor;
    User user;
    String errorMsg;
    Stage mainStage;

    @Init
    public void init() throws Exception {
        FxToolkit.registerStage(() -> new Stage());
    }

    @Stop
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Start
    private void start(Stage stage) throws IOException, AppException {
        // initialisation du mockObject
        MockitoAnnotations.openMocks(this);
        doctor = new Doctor("aaa", "bbb", "123456", "Cardiologie",
                new Establishment("111", "CHUM"));
        user = new User("user", "pass", doctor);
        errorMsg = "Auncun utilisateur trouvé. Veuillez vous assurer d'avoir " +
                "mis le bon nom d'utilisateur et mot de passe.";
        when(dataSourceMock.findByUsernameAndPassword("", ""))
                .thenThrow(new AppException(errorMsg));
        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        mainStage = stage;
        ConnexionController.initialStage(stage, getClass(), new Session(dataSourceMock) );
        mainStage.show();

    }

    @Test
    void checkUiTest(FxRobot robot) {
        FxAssert.verifyThat("#loginButton", LabeledMatchers.hasText("Se Connecter"));
        FxAssert.verifyThat("#username", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#password", TextInputControlMatchers.hasText(""));
    }


    @Test
    void loginClickedFailedTest(FxRobot robot) throws AppException {
        String titleConnexion = "CentRAMQ Accès Médecin - Connexion";
        // when :
        robot.clickOn("#loginButton");
        // then :

        assertEquals(titleConnexion, mainStage.getTitle());
        FxAssert.verifyThat("#errorMessage", TextMatchers.hasText(errorMsg));

        robot.clickOn("#loginButton");
        assertEquals(titleConnexion, mainStage.getTitle());
        FxAssert.verifyThat("#errorMessage", TextMatchers.hasText(errorMsg));
    }

    @Test
    void loginClickedSuccessTest(FxRobot robot) throws AppException {
        String titleHome = "CentRAMQ Accès Médecin - Accueil";
        // when:
        robot.clickOn("#username").write("user");
        robot.clickOn("#password").write("pass");
        FxAssert.verifyThat("#username", TextInputControlMatchers.hasText("user"));
        FxAssert.verifyThat("#password", TextInputControlMatchers.hasText("pass"));
        robot.clickOn("#loginButton").sleep(100);        // then :
        Window window = robot.listWindows().get(0);
        assertEquals(titleHome, mainStage.getTitle());
        FxAssert.verifyThat("#lastName", LabeledMatchers.hasText("bbb"));
        FxAssert.verifyThat("#firstName", LabeledMatchers.hasText("aaa"));
        FxAssert.verifyThat("#permitNumber", LabeledMatchers.hasText("123456"));
    }

}
