package com.uqam.dao;

import com.uqam.model.*;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
    public void findByUsernameAndPasswordTestSuccess() throws AppException {
        String username = "docteur01";
        String password = "111111";
        String expected = "User{username='docteur01', password='3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d', " +
                "doctor=Doctor{firstname='Marsilius', lastname='Dupuis', " +
                "permit='13698', specialty='médecine de famille', " +
                "establishment=Establishment{identification='123456', designation='CHUM de montréal'}}}";
        User user = source.findByUsernameAndPassword(username, password);
        Assertions.assertEquals(expected, user.toString());
    }

    @Test
    public void findByUsernameAndPasswordTestFailed() throws AppException {
        String username = "docteur01";
        String password = "123451335";
        Assertions.assertThrows(AppException.class, () -> source.findByUsernameAndPassword(username, password));
    }


    @Test
    public void findByIdTestSuccess() throws AppException{
        String id = "MORS12452196";
        String expected = "Patient{firstname='Susan', lastname='Morganti', gender=FEMALE, dateOfBirth=1952-02-03, " +
                "birthCity='Montreal', insuranceNumber='MORS12452196', insuranceExpirationDate=2022-02-01, " +
                "contact=Contact{address='2401 rue Ontario Ouest Montreal, QC H2X 1Y8', phone='514-350-9159', " +
                "email='SusanKMorganti@armyspy.com'}, father='null', mother='null'}";
        Folder folder = source.findById(id);
        Patient patient = folder.getOwner();
        Assertions.assertEquals(expected, patient.toString());
    }

    @Test
    public void findByIdTestFailed() throws AppException{
        String id = "MORS452196";
        Folder folder = source.findById(id);
        Assertions.assertNull(folder);
    }

    @Test
    public void updateSuccess() throws AppException{
        String id = "DONL98632897";
        String expected1 = "[]";
        String expected2 = "[Antecedent{beginning=null, end=null, treatment=null, " +
                "diagnostic=Diagnostic{designation='Diabete'}, prescriber=null}]";
        Folder folder1 = source.findById(id);
        Assertions.assertEquals(expected1, folder1.getAntecedents().toString());
        Folder clone1 = folder1.duplicate();
        Antecedent a = new Antecedent();
        a.setDiagnostic(new Diagnostic("Diabete"));
        clone1.addAntecedent(a);
        source.update(clone1);
        Folder folder2 = source.findById(id);
        Assertions.assertEquals(expected2, folder2.getAntecedents().toString());
        Folder clone2 = folder1.duplicate();
        source.update(clone2);
        Folder folder3 = source.findById(id);
        Assertions.assertEquals(expected1, folder3.getAntecedents().toString());
    }

    @Test
    public void archiveModificationSuccess() throws AppException{
        String id = "DONL98632897";
        String expected = "{\"id\":6,\"owner\":{\"id\":6,\"firstname\":\"Luis\",\"lastname\":\"Donaldson\"," +
                "\"gender\":\"MALE\",\"dateOfBirth\":\"1970-05-31\",\"birthCity\":\"Riviere Du Loup\"," +
                "\"insuranceNumber\":\"DONL98632897\",\"insuranceExpirationDate\":\"2021-08-01\"," +
                "\"contact\":{\"id\":6,\"address\":\"3753 Boulevard Laflèche Riviere Du Loup, QC G5R 3Y4\"," +
                "\"phone\":\"418-866-4854\",\"email\":\"LuisADonaldson@teleworm.us\"}},\"visits\":[],\"antecedents\":[]}";
        Folder folder = source.findById(id);
        source.archiveModification(folder);
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        Archive archive = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("database");
            entityManager = entityManagerFactory.createEntityManager();
            archive = entityManager.createQuery("FROM Archive a WHERE a.insuranceNumber = ?1", Archive.class)
                    .setParameter(1, id).getResultList().get(0);
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(archive);
            transaction.commit();
            Assertions.assertEquals(expected, archive.getFolder());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entityManager != null) entityManager.close();
            if (entityManagerFactory != null) entityManagerFactory.close();
        }
    }

}
