package com.uqam.dao;

import com.uqam.model.Folder;
import com.uqam.model.Patient;
import com.uqam.model.User;
import org.junit.jupiter.api.*;


public class DataSourceTest {

    private DataSource source;

    @BeforeEach
    public void setUp() {
        source = new DataSource();
    }

    @AfterEach
    public void tearDown() {
        source = null;
    }

    @Test
    public void findByUsernameAndPasswordTestSuccess(){
        String username = "docteur01";
        String password = "111111";
        String expected = "User{username='docteur01', password='111111', " +
                "doctor=Doctor{firstname='Marsilius', lastname='Dupuis', " +
                "permit='13698', specialty='médecine de famille', " +
                "establishment=Establishment{identification='123456', designation='CHUM de montréal'}}}";
        User user = source.findByUsernameAndPassword(username, password);
        Assertions.assertEquals(expected, user.toString());
    }

    @Test
    public void findByUsernameAndPasswordTestFailed(){
        String username = "docteur01";
        String password = "123451335";
        User user = source.findByUsernameAndPassword(username, password);
        Assertions.assertNull(user);
    }

    @Test
    public void findByIdTestSuccess(){
        String id = "MORS12452196";
        String expected = "Patient{firstname='Susan', lastname='Morganti', gender=FEMALE, " +
                "dateOfBirth=1952-02-03, birthCity='Montreal', insuranceNumber='MORS12452196', " +
                "contact=Contact{address='2401 rue Ontario Ouest Montreal, QC H2X 1Y8', " +
                "phone='514-350-9159', email='SusanKMorganti@armyspy.com'}, parents=[]}";
        Folder folder = source.findById(id);
        Patient patient = folder.getOwner();
        Assertions.assertEquals(expected, patient.toString());
    }

    @Test
    public void findByIdTestFailed(){
        String id = "MORS452196";
        Folder folder = source.findById(id);
        Assertions.assertNull(folder);
    }
}
