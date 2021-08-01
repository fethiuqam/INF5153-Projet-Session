package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PatientTest {

    String dateStr = "1996-02-23";
    List<Patient> parents= new ArrayList<>();
    Date date = Date.valueOf(dateStr);
    Contact contact = new Contact("2054 maissonneuve,h2c 2e2","5149871234","ck191923@ens.uqam.ca");
    Patient patient = new Patient("Mohamed","Rehouma",Gender.MALE,date,"Algiers","REHM26154978",contact);
    Patient patient2 =  new Patient("Siri","Alexia",Gender.FEMALE,date,"Montreal","ales12312312",contact);
    Patient patient3 =  new Patient("Siri","Alexia",Gender.OTHER,date,"3dda","ales123182312",contact);
    Patient patient4 =  new Patient("Siri","Alexia",Gender.OTHER,date,"3dda","ale113182312",contact);
    Patient patient5 =  new Patient("Siri","Alexia",Gender.OTHER,date,"3dda","aleq13p82312",contact);

    @Test
    void testAddPatient() {
        parents.add(patient);
        Assertions.assertEquals(1,parents.size());
    }

    @Test
    void testValidName(){
        Assertions.assertTrue(patient.validName("Rehouma"));
    }
    @Test
    void testValidName2(){
        Assertions.assertFalse(patient.validName(""));
    }

    @Test
    void testvalidGender(){
        Assertions.assertEquals(Gender.MALE,patient.getGender());
    }
    @Test
    void testvalidGender2(){
        Assertions.assertEquals(Gender.FEMALE,patient2.getGender());
    }  @Test
    void testvalidGender3(){
        Assertions.assertEquals(Gender.OTHER,patient3.getGender());
    }

    @Test
    void testValidBirthCity(){
        Assertions.assertTrue(patient.validBirthCity(patient.getBirthCity()));
    }
    @Test
    void testValidBirthCity2(){
        Assertions.assertTrue(patient2.validBirthCity(patient2.getBirthCity()));
    }
    @Test
    void testValidBirthCity3(){
        Assertions.assertFalse(patient3.validBirthCity(patient3.getBirthCity()));
    }

    @Test
    void testValidInsuranceNumber(){
        Assertions.assertTrue(patient.validInsuranceNumber(patient.getInsuranceNumber()));
    }
    @Test
    void testValidInsuranceNumber2(){
        Assertions.assertTrue(patient2.validInsuranceNumber(patient2.getInsuranceNumber()));
    }
    @Test
    void testValidInsuranceNumber3(){
        Assertions.assertFalse(patient3.validInsuranceNumber(patient4.getInsuranceNumber()));
    }
    @Test
    void testValidInsuranceNumber4(){
        Assertions.assertFalse(patient3.validInsuranceNumber(patient5.getInsuranceNumber()));
    }


}
