package com.uqam.model;

import com.uqam.dao.DataSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionTest  {
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

    @BeforeEach
     void setup() {
        doctor = new Doctor("aaa", "bbb", "123456", "Cardiologie",
                new Establishment("111", "CHUM"));
        user = new User("user", "pass", doctor);
        p1 = new Patient("Susan", "Morganti", Gender.FEMALE,
                new Date(1952, 2, 4), "Montreal", "MORS12452196",
                new Contact("2401 rue Ontario Ouest Montreal, QC H2X 1Y8", "514-350-9159",
                        "SusanKMorganti@armyspy.com"));
        t1 = new Treatment("insuline");
        d1 = new Diagnostic("diabète type I");
        v1 = new Visit.VisitBuilder(doctor,new Date(2021, 1, 6))
                .summary("après consultation de la glycémie du patient ")
                .notes("note 1 pour visite 1")
                .diagnostic(d1.getDesignation())
                .treatment(t1.getDesignation())
                .build();
        a1 = new Antecedent(new Date(2021,1,6),null, d1, t1, doctor);
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
    }

    @Test
    public void loginTestTrue() throws AppException {

        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        Session session = new Session(dataSourceMock);
        boolean result = session.login("user", "pass");
        assertTrue(result);
        assertEquals(user.toString(), session.getUser().toString());
    }

    @Test
    public void loginTestFalse() throws AppException {
        when(dataSourceMock.findByUsernameAndPassword("user01", "pass")).thenReturn(null);
        Session session = new Session(dataSourceMock);
        boolean result = session.login("user01", "pass");
        assertFalse(result);
        assertNull(session.getUser());
    }

    @Test
    public void downloadFolderTestTrue() throws AppException{
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        Session session = new Session(dataSourceMock);
        boolean result = session.downloadFolder("MORS12452196");
        assertTrue(result);
        assertEquals(f1.toString(), session.getCurrentFolder().toString());
    }

    @Test
    public void downloadFolderTestFalse() throws AppException{
        when(dataSourceMock.findById("ABCD12345678")).thenReturn(null);
        Session session = new Session(dataSourceMock);
        boolean result = session.downloadFolder("ABCD12345678");
        assertFalse(result);
        assertNull(session.getCurrentFolder());
    }

    @Test
    public void saveFolderTestTrue() throws AppException{
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        Session session = new Session(dataSourceMock);
        session.downloadFolder("MORS12452196");
        when(dataSourceMock.update(session.getCurrentFolder())).thenReturn(true);
        when(dataSourceMock.archiveModification(session.getCurrentFolder())).thenReturn(true);
        session.setModified(true);
        boolean result = session.saveFolder();
        assertTrue(result);
    }

    @Test
    public void saveFolderTestFalse() throws AppException{
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        Session session = new Session(dataSourceMock);
        session.downloadFolder("MORS12452196");
        when(dataSourceMock.update(session.getCurrentFolder())).thenReturn(false);
        session.setModified(true);
        boolean result = session.saveFolder();
        assertFalse(result);
    }

    @Test
    public void saveFolderTestFalse2() throws AppException{
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        Session session = new Session(dataSourceMock);
        session.downloadFolder("MORS12452196");
        when(dataSourceMock.update(session.getCurrentFolder())).thenReturn(true);
        when(dataSourceMock.archiveModification(session.getCurrentFolder())).thenReturn(false);
        session.setModified(true);
        boolean result = session.saveFolder();
        assertFalse(result);
    }

    @Test
    public void resetFolderTestTrue() throws AppException{
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        Session session = new Session(dataSourceMock);
        session.downloadFolder("MORS12452196");
        boolean result = session.resetFolder();
        assertTrue(result);
    }

    @Test
    public void getAntecedentsTest() throws AppException{
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        Session session = new Session(dataSourceMock);
        session.downloadFolder("MORS12452196");
        Set<Antecedent> expected = new HashSet<>(Arrays.asList(new Antecedent[]{a1}));
        assertEquals(expected.toString(), session.getAntecedents().toString());
    }

    @Test
    public void getVisitsTest() throws AppException{
        when(dataSourceMock.findById("MORS12452196")).thenReturn(f1);
        Session session = new Session(dataSourceMock);
        session.downloadFolder("MORS12452196");
        Set<Visit> expected = new HashSet<>(Arrays.asList(new Visit[]{v1}));
        assertEquals(expected.toString(), session.getVisits().toString());
    }

    @Test
    public void getDoctorTest() throws AppException{
        when(dataSourceMock.findByUsernameAndPassword("user", "pass")).thenReturn(user);
        Session session = new Session(dataSourceMock);
        session.login("user", "pass");
        Doctor expected = doctor;
        assertEquals(expected.toString(), session.getDoctor().toString());
    }

}