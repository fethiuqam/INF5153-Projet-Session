package com.uqam.controller;

import com.uqam.dao.DataSource;
import com.uqam.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.matcher.control.TextMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;

@ExtendWith({ApplicationExtension.class, MockitoExtension.class})
public class FolderControllerTest {

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


        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        when(dataSourceMock.update(f1)).thenReturn(true);
        when(dataSourceMock.archiveModification(f1)).thenReturn(true);



        Session session = new Session(dataSourceMock);
        session.login("user", "pass");
        session.downloadFolder("MORS12452196");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/folder.fxml"));
        Parent folderRoot = fxmlLoader.load();
        FolderController controller = fxmlLoader.getController();
        controller.setSession(session);
        Scene scene = new Scene(folderRoot);
        stage.setScene(scene);
        stage.setTitle("CentRAMQ Accès Médecin - Dossier");
        stage.setResizable(false);
        stage.show();
        mainStage = stage;
    }

    @Test
    void checkPatientInformationsTest(FxRobot robot) {
        FxAssert.verifyThat("#name", TextMatchers.hasText("Morganti"));
        FxAssert.verifyThat("#firstName", TextMatchers.hasText("Susan"));
        FxAssert.verifyThat("#insuranceNumber", TextMatchers.hasText("MORS12452196"));
        FxAssert.verifyThat("#expiryDate", TextMatchers.hasText("2022-01-03"));
        FxAssert.verifyThat("#dateOfBirth", TextMatchers.hasText("1952-02-04"));
        FxAssert.verifyThat("#cityOfBirth", TextMatchers.hasText("Montreal"));
        FxAssert.verifyThat("#address", TextMatchers.hasText("2401 rue Ontario Ouest Montreal, QC H2X 1Y8"));
        FxAssert.verifyThat("#phone", TextMatchers.hasText("514-350-9159"));
        FxAssert.verifyThat("#email", TextMatchers.hasText("SusanKMorganti@armyspy.com"));
        FxAssert.verifyThat("#gender", TextMatchers.hasText("FEMALE"));
        FxAssert.verifyThat("#father", TextMatchers.hasText("Non spécifié"));
        FxAssert.verifyThat("#mother", TextMatchers.hasText("Non spécifié"));
    }

    @Test
    void checkAntecedentsListTest(FxRobot robot) {
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasItems(1));
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasListCell(a1));
    }

    @Test
    void checkVisitsListTest(FxRobot robot) {
        FxAssert.verifyThat("#visitListView", ListViewMatchers.hasItems(1));
        FxAssert.verifyThat("#visitListView", ListViewMatchers.hasListCell(v1));
    }

    @Test
    void addNewAntecedentTest(FxRobot robot) {
        String errorAddAtcd = "Le diagnostic et le traitement de l'antécédant sont nécessaires.";
        Antecedent a2 = new Antecedent(Date.valueOf("2020-01-01"), Date.valueOf("2021-01-01"),
                new Diagnostic("dg"), new Treatment("trt"), doctor);
        robot.clickOn("#tabAntecedent");
        robot.clickOn("#addAntecedent");
        FxAssert.verifyThat("#errorMessageAntecedent", TextMatchers.hasText(errorAddAtcd));
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasItems(1));

        robot.clickOn("#antecedentDiagnostic").write("dg");
        FxAssert.verifyThat("#antecedentDiagnostic", TextInputControlMatchers.hasText("dg"));
        robot.clickOn("#addAntecedent");
        FxAssert.verifyThat("#errorMessageAntecedent", TextMatchers.hasText(errorAddAtcd));
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasItems(1));


        robot.clickOn("#antecedentTreatment").write("trt");
        robot.clickOn("#antecedentStartDate").write("2020-01-01").push(KeyCode.ENTER);
        robot.clickOn("#antecedentEndDate").write("2021-01-01").push(KeyCode.ENTER);
        FxAssert.verifyThat("#antecedentTreatment", TextInputControlMatchers.hasText("trt"));
        DatePicker begin = robot.lookup("#antecedentStartDate").query();
        Assertions.assertEquals("2020-01-01", begin.getEditor().getText());
        DatePicker end = robot.lookup("#antecedentEndDate").query();
        Assertions.assertEquals("2021-01-01", end.getEditor().getText());
        robot.clickOn("#addAntecedent");
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasItems(2));
        ListView<Antecedent> listView = robot.lookup("#antecedentsListView").query();
        listView.getSelectionModel().select(1);
        FxAssert.verifyThat(listView, ListViewMatchers.hasSelectedRow(a2));
    }

    @Test
    void modifyAntecedentsTest(FxRobot robot) {
        Antecedent a2 = new Antecedent(Date.valueOf("2021-01-06"), Date.valueOf("2022-08-08"), d1, t1, doctor);
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasItems(1));
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasListCell(a1));
        robot.clickOn("#antecedentsListView");
        Assertions.assertEquals(2, robot.listWindows().size());
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#edit"));
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#diagnosticInputField")).eraseText(30);
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#edit"));
        FxAssert.verifyThat("#errorMessage",TextMatchers.hasText("message erreur") );
        robot.closeCurrentWindow();
        Assertions.assertEquals(1, robot.listWindows().size());

        robot.clickOn("#antecedentsListView");
        Assertions.assertEquals(2, robot.listWindows().size());
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#edit"));
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#dateEndPicker"))
                .write("2022-08-08").push(KeyCode.ENTER);
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#edit"));
        robot.closeCurrentWindow();
        Assertions.assertEquals(1, robot.listWindows().size());
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasItems(1));
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasListCell(a2));
    }

    @Test
    void deleteItemInListAntecedentsTest(FxRobot robot) {
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasItems(1));
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasListCell(a1));
        robot.clickOn("#antecedentsListView");
        Assertions.assertEquals(2, robot.listWindows().size());
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#delete"));
        Assertions.assertEquals(3, robot.listWindows().size());
        robot.clickOn(robot.listWindows().get(2).getScene().lookup("#delete"));
        Assertions.assertEquals(1, robot.listWindows().size());
        FxAssert.verifyThat("#antecedentsListView", ListViewMatchers.hasItems(0));
    }

    @Test
    void modifyVisitTest(FxRobot robot) {
        Visit  v2 = new VisitBuilder(doctor, Date.valueOf("2021-01-06"))
                .summary("après consultation de la glycémie du patient ").notes("note modifiée")
                .diagnostic(d1.getDesignation()).treatment(t1.getDesignation()).build();
        FxAssert.verifyThat("#visitListView", ListViewMatchers.hasItems(1));
        FxAssert.verifyThat("#visitListView", ListViewMatchers.hasListCell(v1));
        robot.clickOn("#visitListView");
        Assertions.assertEquals(2, robot.listWindows().size());
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#apply"));
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#noteInputField")).eraseText(25)
                .write("note modifiée");
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#apply"));
        robot.closeCurrentWindow();
        Assertions.assertEquals(1, robot.listWindows().size());
        FxAssert.verifyThat("#visitListView", ListViewMatchers.hasItems(1));
        FxAssert.verifyThat("#visitListView", ListViewMatchers.hasListCell(v2));
    }

    @Test
    void deleteItemInListVisitsTest(FxRobot robot) {
        FxAssert.verifyThat("#visitListView", ListViewMatchers.hasItems(1));
        FxAssert.verifyThat("#visitListView", ListViewMatchers.hasListCell(v1));
        robot.clickOn("#visitListView");
        Assertions.assertEquals(2, robot.listWindows().size());
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#delete"));
        Assertions.assertEquals(3, robot.listWindows().size());
        robot.clickOn(robot.listWindows().get(2).getScene().lookup("#delete"));
        Assertions.assertEquals(1, robot.listWindows().size());
        FxAssert.verifyThat("#visitListView", ListViewMatchers.hasItems(0));
    }

    @Test
    void addVisitTest(FxRobot robot) {
        String titleHome = "CentRAMQ Accès Médecin - Accueil";
        Assertions.assertFalse(robot.lookup("#newVisitFields").query().isVisible());
        robot.clickOn("#addVisitButton");
        Assertions.assertTrue(robot.lookup("#newVisitFields").query().isVisible());
        robot.clickOn("#visitDiagnostic").write("exemple diagnostic");
        robot.clickOn("#save");
        FxAssert.verifyThat("#errorMessage", TextMatchers.hasText("Le résumé de la visite est nécessaire."));
        robot.clickOn("#visitSummary").write("exemple résumé");
        robot.clickOn("#save");
        assertEquals(titleHome, mainStage.getTitle());
    }
}
