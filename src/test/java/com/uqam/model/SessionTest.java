package com.uqam.model;

import com.uqam.dao.DataSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;

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
                .diagnostic(d1)
                .treatment(t1)
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

}