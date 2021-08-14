package com.uqam.controller;

import com.uqam.dao.DataSource;
import com.uqam.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TextMatchers;
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
    Antecedent a1;
    Folder f1;
    Scene scene;

    @Start
    private void start(Stage stage) throws IOException, AppException {
        MockitoAnnotations.openMocks(this);
        doctor = new Doctor("aaa", "bbb", "123456", "Cardiologie",
                new Establishment("111", "CHUM"));
        user = new User("user", "pass", doctor);
        p1 = new Patient("Susan", "Morganti", Gender.FEMALE,
                Date.valueOf("1952-02-04"), "Montreal", "MORS12452196",
                new Contact("2401 rue Ontario Ouest Montreal, QC H2X 1Y8", "514-350-9159",
                        "SusanKMorganti@armyspy.com"));
        t1 = new Treatment("insuline");
        d1 = new Diagnostic("diabète type I");
        v1 = new VisitBuilder(doctor,Date.valueOf("2021-01-06"))
                .summary("après consultation de la glycémie du patient ")
                .notes("note 1 pour visite 1")
                .diagnostic(d1.getDesignation())
                .treatment(t1.getDesignation())
                .build();
        a1 = new Antecedent(Date.valueOf("2021-01-06"),null, d1, t1, doctor);
        f1 = new Folder(p1, new HashSet(Arrays.asList(new Visit[]{v1})), new HashSet(Arrays.asList(new Antecedent[]{a1})));


        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        when(dataSourceMock.findByUsernameAndPassword("", "")).thenReturn(null);
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        when(dataSourceMock.findById("")).thenReturn(null);

        Session session = new Session(dataSourceMock);
        session.login("user", "pass");
        session.downloadFolder("MORS12452196");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/patient.fxml"));
        Parent folderRoot = fxmlLoader.load();
        FolderController controller = fxmlLoader.getController();
        controller.setSession(session);
        scene = new Scene(folderRoot);
        stage.setScene(scene);
        stage.setTitle("CentRAMQ Accès Médecin");
        stage.setResizable(false);
        stage.show();
    }

    @Test
    void checkPatientInformationsTest(FxRobot robot) {
//        robot.sleep(5000);

        FxAssert.verifyThat("#name", TextMatchers.hasText("Morganti"));
        FxAssert.verifyThat("#firstName", TextMatchers.hasText("Susan"));
        FxAssert.verifyThat("#insuranceNumber", TextMatchers.hasText("MORS12452196"));
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


}
