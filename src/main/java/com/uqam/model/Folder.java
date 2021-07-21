package com.uqam.model;

import java.util.List;

public class Folder {

    private Patient owner;
    private List<Visit> visits;
    private List<Antecedent> antecedents;

    private void addVisit(Visit visite){
        visits.add(visite);
    }
    private void addAntecedent(Antecedent antecedent){
        antecedents.add(antecedent);
    }


    public Patient getPatient() {
        return owner;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public List<Antecedent> getAntecedents() {
        return antecedents;
    }
}
