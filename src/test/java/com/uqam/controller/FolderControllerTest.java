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
    Patient p1;
    Treatment t1;
    Diagnostic d1;
    Visit v1;
    Antecedent a1 ;
    Folder f1 ;
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

        t1 = new Treatment("insuline");
        d1 = new Diagnostic("diab??te type I");
        v1 = new VisitBuilder(doctor, Date.valueOf("2021-01-06"))
                .summary("apr??s consultation de la glyc??mie du patient ")
                .notes("note 1 pour visite 1")
                .diagnostic(d1.getDesignation())
                .treatment(t1.getDesignation())
                .build();
        a1 = new Antecedent(Date.valueOf("2021-01-06"), null, d1, t1, doctor);
        f1 = new Folder(p1, new HashSet(Arrays.asList(new Visit[]{v1})), new HashSet(Arrays.asList(new Antecedent[]{a1})));

        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        when(dataSourceMock.update(f1)).thenReturn(true);
        when(dataSourceMock.archiveModification(f1)).thenReturn(true);

        Session session = new Session(dataSourceMock);
        session.login("user", "pass");
        session.downloadFolder("MORS12452196");

        mainStage = stage;
        FolderController.initialStage(stage, getClass(), session );
        mainStage.show();
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
        FxAssert.verifyThat("#father", TextMatchers.hasText("Non sp??cifi??"));
        FxAssert.verifyThat("#mother", TextMatchers.hasText("Non sp??cifi??"));
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
        String errorAddAtcd = "Le diagnostic et le traitement de l'ant??c??dant sont n??cessaires.";
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
                .summary("apr??s consultation de la glyc??mie du patient ").notes("note modifi??e")
                .diagnostic(d1.getDesignation()).treatment(t1.getDesignation()).build();
        FxAssert.verifyThat("#visitListView", ListViewMatchers.hasItems(1));
        FxAssert.verifyThat("#visitListView", ListViewMatchers.hasListCell(v1));
        robot.clickOn("#visitListView");
        Assertions.assertEquals(2, robot.listWindows().size());
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#apply"));
        robot.clickOn(robot.listWindows().get(1).getScene().lookup("#noteInputField")).eraseText(25)
                .write("note modifi??e");
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
        String titleHome = "CentRAMQ Acc??s M??decin - Accueil";
        Assertions.assertFalse(robot.lookup("#newVisitFields").query().isVisible());
        robot.clickOn("#addVisitButton");
        Assertions.assertTrue(robot.lookup("#newVisitFields").query().isVisible());
        robot.clickOn("#visitDiagnostic").write("exemple diagnostic");
        robot.clickOn("#save");
        FxAssert.verifyThat("#errorMessage", TextMatchers.hasText("Le r??sum?? de la visite est n??cessaire."));
        robot.clickOn("#visitSummary").write("exemple r??sum??");
        robot.clickOn("#save");
        assertEquals(titleHome, mainStage.getTitle());
    }
}
