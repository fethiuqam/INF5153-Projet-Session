package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EstablishementTEst {

    Establishment establishment = new Establishment("A55646","Hopital Juif");

    @Test
    void validValidDesignation(){
        Assertions.assertTrue(establishment.validDesignation(establishment.getDesignation()));
    }
}
