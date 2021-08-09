package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FolderTest {

    private Folder folder;
    private Visit visit;
    private Treatment treatment;
    private Diagnostic diagnostic;
    private Establishment establishment;
    private Doctor doctor;

    @BeforeAll
    void setup() {
        String str = "2021-07-27";
        Date date = Date.valueOf(str);
        Contact contact = new Contact("2054 maissonneuve,h2c 2e2", "5149871234", "ck191923@ens.uqam.ca");
        Patient patient = new Patient("Mohamed", "Rehouma", Gender.MALE, date, "Algiers", "REHM26154978", contact);
        folder = new Folder(patient);

        treatment = new Treatment("The patient must get vaccinated.");
        diagnostic = new Diagnostic("Covid-19");
        establishment = new Establishment("Hosp1234", "Hopital juif");
        doctor = new Doctor("Pablo", "Escobar", "A45123654", "Doctor", establishment);
        visit = new Visit.VisitBuilder(doctor,date)
                .summary("Patient is suffering from covid-19")
                .notes("patient is old")
                .diagnostic(diagnostic)
                .treatment(treatment)
                .build();


    }


    @Test
    void validAddVisit() {
        folder.addVisit(visit);
        Assertions.assertEquals(1, folder.sizeVisit());
    }

    @Test
    void validAddAntecedent() {
        String debut = "2021-06-05";
        String fin = "2021-06-12";
        Date beginning = Date.valueOf(debut);
        Date end = Date.valueOf(fin);
        Antecedent antecedent = new Antecedent(beginning, end, diagnostic, treatment, doctor);
        folder.addAntecedent(antecedent);
        Assertions.assertEquals(1, folder.sizeAntecedent());
    }

}
