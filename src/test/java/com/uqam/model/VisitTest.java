package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;

public class VisitTest {

    Establishment establishment = new Establishment("4564","Hopital Juif");
    Doctor doctor = new Doctor("Laurent","Alexandre", "A451236","Chirurgien",establishment);
    Treatment treatment = new Treatment("Dialise");
    Diagnostic diagnostic = new Diagnostic("Probleme de foie");
    Date dateVisite = Date.valueOf("2021-04-04");
    Visit visit = visit = new VisitBuilder(doctor, dateVisite)
            .summary("Le patient doit filtrer son sang regulierement")
            .notes("Aucune")
            .diagnostic(diagnostic.getDesignation())
            .treatment(treatment.getDesignation())
            .build();

    @Test
    void testClone(){
        Visit visit1 = visit.clone();
        Assertions.assertEquals(visit1.toString(),visit.toString());
    }

}
