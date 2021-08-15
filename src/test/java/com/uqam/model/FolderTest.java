package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Date;
import java.util.*;

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
        Patient patient = new Patient("Mohamed", "Rehouma", Gender.MALE, date, "Algiers", "REHM26154978", Date.valueOf("2022-01-03"), contact);
        folder = new Folder(patient, new HashSet<>(), new HashSet<>());

        treatment = new Treatment("The patient must get vaccinated.");
        diagnostic = new Diagnostic("Covid-19");
        establishment = new Establishment("Hosp1234", "Hopital juif");
        doctor = new Doctor("Pablo", "Escobar", "A45123654", "Doctor", establishment);
        visit = new VisitBuilder(doctor, date)
                .summary("Patient is suffering from covid-19")
                .notes("patient is old")
                .diagnostic(diagnostic.getDesignation())
                .treatment(treatment.getDesignation())
                .build();
    }







    @Test
    void testDuplicate() {

        Date birthDate = Date.valueOf("1970-07-27");
        Date expirationDate = Date.valueOf("2021-07-27");
        Contact contact = new Contact("2054 maissonneuve,h2c 2e2", "5149871234", "ck191923@ens.uqam.ca");
        Patient patient = new Patient("Mohamed", "Rehouma", Gender.MALE, birthDate,
                "Algiers", "REHM26154978", expirationDate, contact);

        String debut = "2021-06-05";
        String fin = "2021-06-12";
        Date beginning = Date.valueOf(debut);
        Date end = Date.valueOf(fin);
        Antecedent antecedent = new Antecedent(beginning, end, diagnostic, treatment, doctor);

        String dateVisite = "2021-01-04";
        Date dateVisite2 = Date.valueOf(dateVisite);

        Treatment treatment = new Treatment("Aucun traitement");
        Diagnostic diagnostic = new Diagnostic("Aucune maladie");

        Establishment establishment = new Establishment("Hosp1234", "Hopital juif");

        Doctor doctor = new Doctor("Pablo", "Escobar", "A45123654", "Doctor", establishment);

        Visit visit = new VisitBuilder(doctor, dateVisite2)
                .summary("Aucune maladie")
                .notes("Aucune note")
                .diagnostic(diagnostic.getDesignation())
                .treatment(treatment.getDesignation())
                .build();

        Set<Visit> visitList = new HashSet<>();
        visitList.add(visit);

        Set<Antecedent> antecedentSet = new HashSet<>();
        antecedentSet.add(antecedent);
        folder = new Folder(patient, visitList, antecedentSet);

        Folder folder2 = folder.duplicate();
        Assertions.assertEquals(folder.getOwner().getInsuranceNumber(), folder2.getOwner().getInsuranceNumber());
        Assertions.assertEquals(folder.getOwner().getFirstname(), folder2.getOwner().getFirstname());
        Assertions.assertEquals(folder.getOwner().getLastname(), folder2.getOwner().getLastname());
        Assertions.assertEquals(folder.getOwner().getGender(), folder2.getOwner().getGender());
        Assertions.assertEquals(folder.getOwner().getMother(), folder2.getOwner().getMother());
        Assertions.assertEquals(folder.getOwner().getFather(), folder2.getOwner().getFather());
        Assertions.assertEquals(folder.getOwner().getBirthCity(), folder2.getOwner().getBirthCity());


        Assertions.assertEquals(folder.toString(), folder2.toString());


    }


}
