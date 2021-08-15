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

    Visit visit = new Visit(dateVisite,"Le patient doit filtrer son sang regulierement","Aucune",treatment,diagnostic,doctor);


    @Test
    void testClone(){
        Visit visit1 = visit.clone();
        Assertions.assertEquals(visit1.toString(),visit.toString());
    }

    @Test
    void testBuilder(){
        Visit.VisitBuilder visitBuilder = new Visit.VisitBuilder(doctor,dateVisite);
        visitBuilder.treatment(treatment);
        visitBuilder.notes("Aucune note");
        visitBuilder.summary("Aucun resume");
        visitBuilder.diagnostic(diagnostic);
        Visit visit =  visitBuilder.build();
        Assertions.assertEquals("Hopital Juif",visit.getDoctor().getEstablishment().getDesignation());
        Assertions.assertEquals("Chirurgien",visit.getDoctor().getSpecialty());
        Assertions.assertEquals("A451236",visit.getDoctor().getPermit());
        Assertions.assertEquals("Laurent",visit.getDoctor().getFirstname());
        Assertions.assertEquals("Alexandre",visit.getDoctor().getLastname());
        Assertions.assertEquals("Aucune note",visit.getNotes());
        Assertions.assertEquals("Aucun resume",visit.getSummary());
        Assertions.assertEquals(dateVisite,visit.getDate());
        Assertions.assertEquals("Probleme de foie",visit.getDiagnostic().getDesignation());

    }
}
