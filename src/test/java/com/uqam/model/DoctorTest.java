package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    void validSpeciality(){
        Assertions.assertTrue(doctor.validSpeciality(doctor.getSpecialty()));
    }
}
