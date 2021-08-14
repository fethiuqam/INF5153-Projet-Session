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
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.matcher.control.TextInputControlMatchers;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import static org.mockito.Mockito.when;


@ExtendWith({ApplicationExtension.class, MockitoExtension.class})
public class ConnexionControllerTest {

    @Mock
    DataSource dataSourceMock;
    Doctor doctor;
    User user;
    Scene scene;

    @Start
    private void start(Stage stage) throws IOException, AppException {
        // initialisation du mockObject
        MockitoAnnotations.openMocks(this);
        doctor = new Doctor("aaa", "bbb", "123456", "Cardiologie",
                new Establishment("111", "CHUM"));
        user = new User("user", "pass", doctor);
        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        when(dataSourceMock.findByUsernameAndPassword("", "")).thenReturn(null);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Parent connexionRoot = (Parent) fxmlLoader.load();
        ConnexionController controller = fxmlLoader.getController();
        controller.setSession(new Session(dataSourceMock));
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
        // when :
        robot.clickOn("#loginButton");
        // then :
        Window window = robot.listWindows().get(0);
        assertEquals(scene, window.getScene());
    }

    @Test
    void loginClickedSuccessTest(FxRobot robot) throws AppException {
        // when:
        robot.clickOn("#username").write("user");
        robot.clickOn("#password").write("pass");
        FxAssert.verifyThat("#username", TextInputControlMatchers.hasText("user"));
        FxAssert.verifyThat("#password", TextInputControlMatchers.hasText("pass"));
        robot.clickOn("#loginButton").sleep(100);        // then :
        Window window = robot.listWindows().get(0);
        assertNotEquals(scene, window.getScene());
        FxAssert.verifyThat("#lastName", LabeledMatchers.hasText("bbb"));
        FxAssert.verifyThat("#firstName", LabeledMatchers.hasText("aaa"));
        FxAssert.verifyThat("#permitNumber", LabeledMatchers.hasText("123456"));

    }

}
