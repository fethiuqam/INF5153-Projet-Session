package com.uqam.controller;

import com.uqam.dao.DataSource;
import com.uqam.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.matcher.control.TextInputControlMatchers;

import static org.testfx.assertions.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

@ExtendWith({ApplicationExtension.class})
public class HomeControllerTest {

    Scene scene;

    @Start
    private void start(Stage stage) throws IOException, AppException {

        Session session = new Session(new DataSource());
        session.login("docteur01", "111111");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
        Parent homeRoot = fxmlLoader.load();
        HomeController controller = fxmlLoader.getController();
        controller.setSession(session);
        scene = new Scene(homeRoot);
        stage.setScene(scene);
        stage.setTitle("CentRAMQ Accès Médecin");
        stage.setResizable(false);
        stage.show();
    }

    @Test
    void checkUiTest(FxRobot robot) {

        FxAssert.verifyThat("#searchButton", LabeledMatchers.hasText("Rechercher"));
        FxAssert.verifyThat("#insuranceSearchQuery", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#scanButton", LabeledMatchers.hasText("Scanner"));
        FxAssert.verifyThat("#cancelScanButton", LabeledMatchers.hasText("Annuler"));
        assertTrue(robot.listWindows().get(0).getScene().lookup("#scanButton").isVisible());
        assertTrue(robot.listWindows().get(0).getScene().lookup("#cancelScanButton").isVisible());
        // FxAssert.verifyThat("#logoutButton", LabeledMatchers.hasText("Déconnexion")); c'est pas un bouton
        FxAssert.verifyThat("#lastName", LabeledMatchers.hasText("Dupuis"));
        FxAssert.verifyThat("#firstName", LabeledMatchers.hasText("Marsilius"));
        FxAssert.verifyThat("#permitNumber", LabeledMatchers.hasText("13698"));
    }

    @Test
    void searchClickedFailedTest(FxRobot robot) throws AppException {
        // when :
        robot.clickOn("#searchButton");
        // then :
        Window window = robot.listWindows().get(0);
        assertEquals(scene, window.getScene());
    }

    @Test
    void searchClickedSuccessTest(FxRobot robot) throws AppException {
        // when:
        robot.clickOn("#insuranceSearchQuery").write("MORS12452196");
        FxAssert.verifyThat("#insuranceSearchQuery", TextInputControlMatchers.hasText("MORS12452196"));
        robot.clickOn("#searchButton");
        // then :
        Window window = robot.listWindows().get(0);
        assertNotEquals(scene, window.getScene());
    }

    @Test
    void logoutClickedTest(FxRobot robot) throws AppException {
        // when:
        robot.clickOn("#logoutButton");
        // then :
        Window window = robot.listWindows().get(0);
        assertNotEquals(scene, window.getScene());
    }

    @Test
    void showHideScanCardTest(FxRobot robot) throws AppException {
        assertTrue(robot.listWindows().get(0).getScene().lookup("#cardScan").isVisible());
        // when:

        robot.clickOn("#scanButton");
        // then :
        assertTrue(robot.listWindows().get(0).getScene().lookup("#cardScan").isVisible());
        robot.clickOn("#cancelScanButton");
        assertTrue(robot.listWindows().get(0).getScene().lookup("#cardScan").isVisible());


    }
}
