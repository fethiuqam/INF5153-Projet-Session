package com.uqam.model;

import java.util.List;

public class Folder {

    private Patient owner;
    private List<Visit> visits;
    private List<Antecedent> antecedents;

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
