package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EstablishementTEst {

    Establishment establishment = new Establishment("A55646","Hopital Juif");

    @Test
    void validValidDesignation() throws AppException {
        Assertions.assertTrue(establishment.validDesignation(establishment.getDesignation()));
    }

    @Test
    void testCloneEstablishment() throws CloneNotSupportedException {
        Establishment establishment2 = establishment.clone();
        Assertions.assertEquals(establishment.getId(),establishment2.getId());
        Assertions.assertEquals(establishment.getDesignation(),establishment2.getDesignation());
    }

    @Test
    void testToString(){
        Assertions.assertEquals("Establishment{identification='A55646', designation='Hopital Juif'}",establishment.toString());
    }
    
}
