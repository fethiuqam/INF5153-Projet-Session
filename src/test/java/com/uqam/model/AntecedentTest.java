package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;

public class AntecedentTest {

    Establishment establishment = new Establishment("X987645", "Hopital Juif");
    Doctor doctor =  new Doctor("donald","trump","A123456","Doctor",establishment);
    Diagnostic diagnostic = new Diagnostic("Covid19");
    Treatment treatment = new Treatment("Vaccin");
    Date debut = Date.valueOf("2021-05-21");
    Date fin = Date.valueOf("2021-05-29");

    Antecedent antecedent   = new Antecedent(debut,fin,diagnostic,treatment,doctor);
    Antecedent antecedent1 = antecedent.clone();
    @Test
    void testClone(){



        Assertions.assertEquals(antecedent.getBeginning(),antecedent1.getBeginning());
        Assertions.assertEquals(antecedent.getEnd(),antecedent1.getEnd());
        Assertions.assertEquals(antecedent.getBeginning(),antecedent1.getBeginning());
    }

    @Test
    void testToString(){
        Assertions.assertEquals("Antecedent{beginning=2021-05-21, end=2021-05-29, treatment=Treatment{designation='Vaccin'}, diagnostic=Diagnostic{designation='Covid19'}, prescriber=Doctor{firstname='donald', lastname='trump', permit='A123456', specialty='Doctor', establishment=Establishment{identification='X987645', designation='Hopital Juif'}}}",antecedent.toString());

    }
}
