package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreatmentTest {


    Treatment treatment = new Treatment("Vaccin Moderna");
    @Test
    void testValidTreatment() throws AppException {
        Assertions.assertTrue(treatment.validTreatment(treatment.getDesignation()));
    }

    @Test
    void testInvalidTreatment(){
        Assertions.assertThrows(AppException.class, ()  ->{
            Treatment treatment = new Treatment("miel");
            treatment.validTreatment(treatment.getDesignation());
        });
    }

    @Test
    void testClone(){
        Treatment treatment2 = treatment.clone();
        Assertions.assertEquals(treatment2.getDesignation(),treatment.getDesignation());
    }

    @Test
    void testToString(){
        Assertions.assertEquals("Treatment{designation='Vaccin Moderna'}",treatment.toString());
    }

}
