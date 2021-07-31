package com.uqam.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Date;

public class FolderTest {

    String str = "2021-07-27";
    Date date = Date.valueOf(str);
    Contact contact = new Contact("2054 maissonneuve,h2c 2e2","5149871234","ck191923@ens.uqam.ca");
    Patient patient = new Patient("Mohamed","Rehouma",Gender.MALE,date,"Algiers","REHM26154978",contact);
    Folder folder = new Folder(patient);

    Treatment treatment = new Treatment("The patient must get vaccinated.");
    Diagnostic diagnostic = new Diagnostic("Covid-19");
    Establishment establishment = new Establishment("Hosp1234","Hopital juif");
    Doctor doctor = new Doctor("Pablo","Escobar","A45123654","Doctor", establishment);
    Visit visit = new Visit(date,"Patient is suffering from covid-19","patient is old",treatment,diagnostic,doctor);

    String debut = "2021-06-05";
    String fin = "2021-06-12";
    Date dateDebut = Date.valueOf(debut);
    Date datefin = Date.valueOf(fin);


    @Test
    void validAddVisit(){
        folder.addVisit(visit);
        Assertions.assertEquals(1,folder.sizeVisit());
    }

    @Test
    void validAddAntecedent(){
        Antecedent antecedent = new Antecedent(dateDebut,datefin,diagnostic,treatment,doctor);
        folder.addAntecedent(antecedent);
        Assertions.assertEquals(1,folder.sizeAntecedent());
    }



}
