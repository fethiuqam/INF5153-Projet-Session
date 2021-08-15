package com.uqam.model;

import com.uqam.dao.DataSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionTest {
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
    Session session;

    @BeforeEach
    void setup() {
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
        v1 = new VisitBuilder(doctor, new Date(2021, 1, 6))
                .summary("après consultation de la glycémie du patient ")
                .notes("note 1 pour visite 1")
                .diagnostic(d1.getDesignation())
                .treatment(t1.getDesignation())
                .build();
        a1 = new Antecedent(new Date(2021, 1, 6), null, d1, t1, doctor);
        f1 = new Folder(p1, new HashSet(Arrays.asList(new Visit[]{v1})), new HashSet(Arrays.asList(new Antecedent[]{a1})));

    }

    @AfterEach
    void tearDown() {
        f1 = null;
        a1 = null;
        v1 = null;
        d1 = null;
        t1 = null;
        p1 = null;
        user = null;
        doctor = null;
        session = null;
    }

    @Test
    public void loginTestTrue() throws AppException {

        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        session = new Session(dataSourceMock);
        boolean result = session.login("user", "pass");
        assertTrue(result);
        assertEquals(user.toString(), session.getUser().toString());
    }

    @Test
    public void loginTestFalse() throws AppException {
        String errorMsg = "Auncun utilisateur trouvé. Veuillez vous assurer d'avoir " +
                "mis le bon nom d'utilisateur et mot de passe.";
        when(dataSourceMock.findByUsernameAndPassword("user01", "pass"))
                .thenThrow(new AppException(errorMsg));
        session = new Session(dataSourceMock);
        Throwable exceptionThrown = assertThrows(AppException.class, () -> {
            session.login("user01", "pass");
        });
        assertEquals(exceptionThrown.getMessage(), errorMsg);
    }

    @Test
    public void downloadFolderTestTrue() throws AppException {
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        session = new Session(dataSourceMock);
        boolean result = session.downloadFolder("MORS12452196");
        assertTrue(result);
        assertEquals(f1.toString(), session.getCurrentFolder().toString());
    }

    @Test
    public void downloadFolderTestFailed() throws AppException {
        String errorMsg = "Aucun numero d'assurance maladie correspondant trouvé.";
        when(dataSourceMock.findById("ABCD12345678")).thenThrow(new AppException(errorMsg));
        session = new Session(dataSourceMock);
        Throwable exceptionThrown = assertThrows(AppException.class, () -> {
            session.downloadFolder("ABCD12345678");
        });
        assertEquals(exceptionThrown.getMessage(), errorMsg);
        assertNull(session.getCurrentFolder());
    }

    @Test
    public void saveFolderTestTrue() throws AppException {
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        session = new Session(dataSourceMock);
        session.downloadFolder("MORS12452196");
        session.getCurrentFolder().getAntecedents().clear();
        boolean result = session.saveFolder();
        assertTrue(result);
    }


    @Test
    public void resetFolderTestTrue() throws AppException {
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        session = new Session(dataSourceMock);
        session.downloadFolder("MORS12452196");
        boolean result = session.resetFolder();
        assertTrue(result);
    }

    @Test
    public void getAntecedentsTest() throws AppException {
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        session = new Session(dataSourceMock);
        session.downloadFolder("MORS12452196");
        Set<Antecedent> expected = new HashSet<>(Arrays.asList(new Antecedent[]{a1}));
        assertEquals(expected.toString(), session.getAntecedents().toString());
    }

    @Test
    public void getVisitsTest() throws AppException {
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        session = new Session(dataSourceMock);
        session.downloadFolder("MORS12452196");
        Set<Visit> expected = new HashSet<>(Arrays.asList(new Visit[]{v1}));
        assertEquals(expected.toString(), session.getVisits().toString());
    }

    @Test
    public void getDoctorTest() throws AppException {
        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        session = new Session(dataSourceMock);
        session.login("user", "pass");
        Doctor expected = doctor;
        assertEquals(expected.toString(), session.getDoctor().toString());
    }

}