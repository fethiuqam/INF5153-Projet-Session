package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.print.Doc;

public class DoctorTest {

    Establishment establishment = new Establishment("AFX879","Hopital de Montreal");
    Doctor doctor = new Doctor("Laurent","Alexandre","X45128210","Chirurgien cardiaque",establishment);
    @Test
    void validValidFirstName() throws AppException {
        Assertions.assertTrue(doctor.validName(doctor.getFirstname()));
    }
    @Test
    void validValidLastName() throws AppException {
        Assertions.assertTrue(doctor.validName(doctor.getLastname()));
    }

    @Test
    void validSpeciality() throws AppException {
        Assertions.assertTrue(doctor.validSpeciality(doctor.getSpecialty()));
    }

    @Test
    void validCloneDoctor() throws CloneNotSupportedException {
        Doctor doctor2 = doctor.clone();

        Assertions.assertEquals(doctor.getFirstname(),doctor2.getFirstname());
        Assertions.assertEquals(doctor.getLastname(),doctor2.getLastname());
        Assertions.assertEquals(doctor.getPermit(),doctor2.getPermit());
        Assertions.assertEquals(doctor.getSpecialty(),doctor2.getSpecialty());
        Assertions.assertEquals(doctor.getEstablishment().getDesignation(),doctor2.getEstablishment().getDesignation());
        Assertions.assertEquals(doctor.getEstablishment().getId(),doctor2.getEstablishment().getId());
    }

    @Test
    void testInvalidCloneDoctor(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            Doctor doctor = new Doctor();
            Doctor doctor1 = doctor.clone();
        });
    }

    @Test
    void testToString(){
        Assertions.assertEquals("Doctor{firstname='Laurent', lastname='Alexandre', permit='X45128210', specialty='Chirurgien cardiaque', establishment=Establishment{identification='AFX879', designation='Hopital de Montreal'}}",doctor.toString());
    }
}
