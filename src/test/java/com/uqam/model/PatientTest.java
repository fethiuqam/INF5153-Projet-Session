package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PatientTest {


    List<Patient> parents= new ArrayList<>();
    Date date = Date.valueOf("1996-02-23");
    Date expirationDate = Date.valueOf("2021-07-27");
    Contact contact = new Contact("2054 maisonneuve,h2c 2e2","5149871234","ck191923@ens.uqam.ca");
    Patient patient = new Patient("Mohamed","Rehouma",Gender.MALE,date,"Algiers","REHM26154978",expirationDate, contact);
    Patient patient2 =  new Patient("Siri","Alexia",Gender.FEMALE,date,"Montreal","ales12312312",expirationDate, contact);
    Patient patient4 =  new Patient("Siri","Alexia",Gender.OTHER,date,"3dda","11111111111666",expirationDate, contact);
    Patient patient5 =  new Patient("Siri","Alexia",Gender.OTHER,date,"3dda","278482312451",expirationDate, contact);
    Patient patient6 =  new Patient("Siri","Alexia",Gender.OTHER,date,"3dda","ABCDEFGHTUJB",expirationDate, contact);

    @Test
    void testAddPatient() {
        parents.add(patient);
        Assertions.assertEquals(1,parents.size());
    }

    @Test
    void testValidName() throws AppException {
        Assertions.assertTrue(patient.validName("Rehouma"));
    }


    @Test
    void testValidName2() {
        Assertions.assertThrows(AppException.class, () -> {
          patient6.validBirthCity(patient6.getBirthCity());
        });
    }

    @Test
    void testInvalidName(){
        Assertions.assertThrows(AppException.class, () ->{
            Patient patient5 =  new Patient("S","Alexia",Gender.OTHER,date,"3dda","aleq13p82312",expirationDate, contact);
            patient5.validName(patient5.getFirstname());
        });
    }

    @Test
    void testValidBirthCity() throws AppException {
        Assertions.assertTrue(patient.validBirthCity(patient.getBirthCity()));
    }
    @Test
    void testValidBirthCity2() throws AppException {
        Assertions.assertTrue(patient2.validBirthCity(patient2.getBirthCity()));
    }
    @Test
    void testValidBirthCity3() {
        Assertions.assertThrows(AppException.class, () -> {
        patient5.validBirthCity(patient5.getBirthCity());
        });
    }

    @Test
    void testValidInsuranceNumber() throws AppException {
        Assertions.assertTrue(patient.validInsuranceNumber(patient.getInsuranceNumber()));
    }
    @Test
    void testValidInsuranceNumber2() throws AppException {
        Assertions.assertTrue(patient2.validInsuranceNumber(patient2.getInsuranceNumber()));
    }
    @Test
    void testValidInsuranceNumber3(){
        Assertions.assertThrows(AppException.class, () ->{
            patient4.validInsuranceNumber(patient4.getInsuranceNumber());
        });
    }
    @Test
    void testValidInsuranceNumber4(){
        Assertions.assertThrows(AppException.class, () ->{
            patient4.validInsuranceNumber(patient6.getInsuranceNumber());
        });
    }

    @Test
    void testValidInsuranceNumber5(){
        Assertions.assertThrows(AppException.class, () ->{
            patient4.validInsuranceNumber(patient5.getInsuranceNumber());
        });
    }

    @Test
    void testValidToString(){
        String expected = "Patient{firstname='Mohamed', lastname='Rehouma', gender=MALE, dateOfBirth=1996-02-23, " +
                "birthCity='Algiers', insuranceNumber='REHM26154978', insuranceExpirationDate=2021-07-27, " +
                "contact=Contact{address='2054 maisonneuve,h2c 2e2', phone='5149871234', email='ck191923@ens.uqam.ca'}, " +
                "father='null', mother='null'}";
        Assertions.assertEquals(expected,patient.toString());
    }

    @Test
    void testClone(){
        Patient clonePatient = patient.clone();
        Assertions.assertEquals(patient.toString(),clonePatient.toString()
        );
    }




}
