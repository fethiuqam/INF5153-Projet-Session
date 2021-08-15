package com.uqam.controller;

import com.uqam.dao.DataSource;
import com.uqam.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.Assertions;
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
    Patient p1, p2;
    Treatment t1, t2;
    Diagnostic d1, d2;
    Visit v1 , v2;
    Antecedent a1 , a2;
    Folder f1 , f2;
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
        MockitoAnnotations.openMocks(this);
        doctor = new Doctor("aaa", "bbb", "123456", "Cardiologie",
                new Establishment("111", "CHUM"));
        user = new User("user", "pass", doctor);

        p1 = new Patient("Susan", "Morganti", Gender.FEMALE,
                Date.valueOf("1952-02-04"), "Montreal", "MORS12452196", Date.valueOf("2022-01-03"),
                new Contact("2401 rue Ontario Ouest Montreal, QC H2X 1Y8", "514-350-9159",
                        "SusanKMorganti@armyspy.com"));
        p2 = new Patient("David", "Pokorny", Gender.MALE,
                Date.valueOf("1982-09-03"), "Montreal", "POKD63259145", Date.valueOf("2020-12-31"),
                new Contact("2864 rue Levy Montreal, QC H3C 5K4", "514-926-9832",
                        "DavidPokorny@yahoo.com"));

        t1 = new Treatment("insuline");
        d1 = new Diagnostic("diabète type I");
        t2 = new Treatment("antibiotique cephalosporine");
        d2 = new Diagnostic("bronchite");

        v1 = new VisitBuilder(doctor, Date.valueOf("2021-01-06"))
                .summary("après consultation de la glycémie du patient ")
                .notes("note 1 pour visite 1")
                .diagnostic(d1.getDesignation())
                .treatment(t1.getDesignation())
                .build();
        v2 = new VisitBuilder(doctor, Date.valueOf("2021-02-12"))
                .summary("symptomes de toux et de dyspnée")
                .diagnostic(d2.getDesignation())
                .treatment(t2.getDesignation())
                .build();

        a1 = new Antecedent(Date.valueOf("2021-01-06"), null, d1, t1, doctor);
        a2 = new Antecedent(Date.valueOf("2021-02-12"), null, d2, t2, doctor);

        f1 = new Folder(p1, new HashSet(Arrays.asList(new Visit[]{v1})), new HashSet(Arrays.asList(new Antecedent[]{a1})));
        f2 = new Folder(p2, new HashSet(Arrays.asList(new Visit[]{v2})), new HashSet(Arrays.asList(new Antecedent[]{a2})));

        errorMsg = "Aucun numero d'assurance maladie correspondant trouvé.";

        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        when(dataSourceMock.findById("POKD63259145")).thenReturn(f2);
        when(dataSourceMock.findById("")).thenThrow(new AppException(errorMsg));

        Session session = new Session(dataSourceMock);
        session.login("user", "pass");
        mainStage = stage;
        HomeController.initialStage(stage, getClass(), session );
        mainStage.show();
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
    void searchClickedSuccessCardExpiredTest(FxRobot robot) throws AppException {
        String titleFolder = "CentRAMQ Accès Médecin - Dossier";
        // when:
        robot.clickOn("#insuranceSearchQuery").write("POKD63259145");
        FxAssert.verifyThat("#insuranceSearchQuery", TextInputControlMatchers.hasText("POKD63259145"));
        robot.clickOn("#searchButton");
        // then :
        Assertions.assertEquals(2, robot.listWindows().size());
        robot.closeCurrentWindow();
        assertEquals(titleFolder, mainStage.getTitle());
        FxAssert.verifyThat("#expiryDate", TextMatchers.hasText("2020-12-31"));

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
