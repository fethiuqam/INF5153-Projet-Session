package com.uqam.controller;

import com.uqam.dao.DataSource;
import com.uqam.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.matcher.control.TextMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;

@ExtendWith({ApplicationExtension.class, MockitoExtension.class})
public class HomeControllerTest {

    @Mock
    DataSource dataSourceMock;
    Doctor doctor;
    User user;
    Patient p1;
    Treatment t1;
    Diagnostic d1;
    Visit v1;
    Antecedent a1;
    Folder f1;
    String errorMsg;
    Stage mainStage;

    @Start
    private void start(Stage stage) throws IOException, AppException {
        MockitoAnnotations.openMocks(this);
        doctor = new Doctor("aaa", "bbb", "123456", "Cardiologie",
                new Establishment("111", "CHUM"));
        user = new User("user", "pass", doctor);
        p1 = new Patient("Susan", "Morganti", Gender.FEMALE,
                new Date(1952, 2, 4), "Montreal", "MORS12452196", Date.valueOf("2022-01-03"),
                new Contact("2401 rue Ontario Ouest Montreal, QC H2X 1Y8", "514-350-9159",
                        "SusanKMorganti@armyspy.com"));
        t1 = new Treatment("insuline");
        d1 = new Diagnostic("diabète type I");
        v1 = new VisitBuilder(doctor,new Date(2021, 1, 6))
                .summary("après consultation de la glycémie du patient ")
                .notes("note 1 pour visite 1")
                .diagnostic(d1.getDesignation())
                .treatment(t1.getDesignation())
                .build();
        a1 = new Antecedent(new Date(2021,1,6),null, d1, t1, doctor);
        f1 = new Folder(p1, new HashSet(Arrays.asList(new Visit[]{v1})), new HashSet(Arrays.asList(new Antecedent[]{a1})));
        errorMsg = "Aucun numero d'assurance maladie correspondant trouvé.";


        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        when(dataSourceMock.findById("")).thenThrow(new AppException(errorMsg));

        Session session = new Session(dataSourceMock);
        session.login("user", "pass");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
        Parent homeRoot = fxmlLoader.load();
        HomeController controller = fxmlLoader.getController();
        controller.setSession(session);
        Scene scene = new Scene(homeRoot);
        stage.setScene(scene);
        stage.setTitle("CentRAMQ Accès Médecin - Accueil");
        stage.setResizable(false);
        stage.show();
        mainStage = stage;
    }

    @Test
    void checkUiTest(FxRobot robot) {

        FxAssert.verifyThat("#searchButton", LabeledMatchers.hasText("Rechercher"));
        FxAssert.verifyThat("#insuranceSearchQuery", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#scanButton", LabeledMatchers.hasText("Scanner"));
        FxAssert.verifyThat("#scanButton", NodeMatchers.isVisible());
        FxAssert.verifyThat("#cancelScanButton", LabeledMatchers.hasText("Annuler"));
        FxAssert.verifyThat("#cancelScanButton", NodeMatchers.isVisible());

        FxAssert.verifyThat("#lastName", LabeledMatchers.hasText("bbb"));
        FxAssert.verifyThat("#firstName", LabeledMatchers.hasText("aaa"));
        FxAssert.verifyThat("#permitNumber", LabeledMatchers.hasText("123456"));
    }

    @Test
    void searchClickedFailedTest(FxRobot robot) throws AppException {
        String titleHome = "CentRAMQ Accès Médecin - Accueil";
        // when :
        robot.clickOn("#searchButton");
        // then :
        assertEquals(titleHome, mainStage.getTitle());
        FxAssert.verifyThat("#errorMessage", TextMatchers.hasText(errorMsg));

        robot.clickOn("#searchButton");
        assertEquals(titleHome, mainStage.getTitle());
        FxAssert.verifyThat("#errorMessage", TextMatchers.hasText(errorMsg));
    }

    @Test
    void searchClickedSuccessTest(FxRobot robot) throws AppException {
        String titleFolder = "CentRAMQ Accès Médecin - Dossier";
        // when:
        robot.clickOn("#insuranceSearchQuery").write("MORS12452196");
        FxAssert.verifyThat("#insuranceSearchQuery", TextInputControlMatchers.hasText("MORS12452196"));
        robot.clickOn("#searchButton");
        // then :
        assertEquals(titleFolder, mainStage.getTitle());

    }

    @Test
    void logoutClickedTest(FxRobot robot) throws AppException {
        String titleConnexion = "CentRAMQ Accès Médecin - Connexion";
        // when:
        robot.clickOn("#logoutButton");
        // then :
        assertEquals(titleConnexion, mainStage.getTitle());

    }

    @Test
    void showHideScanCardTest(FxRobot robot) throws AppException {
        assertTrue(robot.listWindows().get(0).getScene().lookup("#cardScan").isVisible());
        // when:
        robot.clickOn("#scanButton");
        // then :
        assertTrue(robot.lookup("#cardScan").query().isVisible());
        robot.clickOn("#cancelScanButton");
        assertTrue(robot.lookup("#cardScan").query().isVisible());
    }
}
