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
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.matcher.control.TextInputControlMatchers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import java.io.IOException;

@ExtendWith({ApplicationExtension.class, MockitoExtension.class})
public class ConnexionControllerTest {

    @Mock
    DataSource dataSourceMock;
    Doctor doctor;
    User user;
    Scene scene;

    @Start
    private void start(Stage stage) throws IOException, AppException {
        doctor = new Doctor("aaa", "bbb", "123456", "Cardiologie",
                new Establishment("111", "CHUM"));
        user = new User("user", "pass", doctor);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Parent connexionRoot = (Parent) fxmlLoader.load();
        ConnexionController controller = fxmlLoader.getController();
        controller.setSession(new Session(new DataSource()));
        scene = new Scene(connexionRoot);
        stage.getIcons().add(new Image("/images/windowIcon.png"));
        stage.setTitle("CentRAMQ Accès Médecin");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Test
    void checkUiTest(FxRobot robot) {
        FxAssert.verifyThat("#loginButton", LabeledMatchers.hasText("Se Connecter"));
        FxAssert.verifyThat("#username", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#password", TextInputControlMatchers.hasText(""));
    }


    @Test
    void loginClickedFailedTest(FxRobot robot) throws AppException {
        lenient().when(dataSourceMock.findByUsernameAndPassword("", "")).thenReturn(null);
        // when :
        robot.clickOn("#loginButton");
        // then :
        Window window = robot.listWindows().get(0);
        assertEquals(scene, window.getScene());
    }

    @Test
    void loginClickedSuccessTest(FxRobot robot) throws AppException {
        lenient().when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        // when:
        robot.clickOn("#username").write("docteur01");
        robot.clickOn("#password").write("111111");
        FxAssert.verifyThat("#username", TextInputControlMatchers.hasText("docteur01"));
        FxAssert.verifyThat("#password", TextInputControlMatchers.hasText("111111"));
        robot.clickOn("#loginButton");
        // then :
        Window window = robot.listWindows().get(0);
        assertNotEquals(scene, window.getScene());
        FxAssert.verifyThat("#lastName", LabeledMatchers.hasText("Dupuis"));
        FxAssert.verifyThat("#firstName", LabeledMatchers.hasText("Marsilius"));
        FxAssert.verifyThat("#permitNumber", LabeledMatchers.hasText("13698"));

    }

}
