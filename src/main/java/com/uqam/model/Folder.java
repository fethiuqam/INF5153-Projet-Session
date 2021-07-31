package com.uqam.model;

import java.util.ArrayList;
import java.util.List;

public class Folder {

    private Patient owner;
    private List<Visit> visits = new ArrayList<Visit>();
    private List<Antecedent> antecedents = new ArrayList<Antecedent>();

    //method to return size of visits
    public int sizeVisit(){
        return visits.size();
    }

    //method to return size of antecedent
    public int sizeAntecedent(){
        return antecedents.size();
    }

    //methode pour rajouter une visite
    public void addVisit(Visit visite){
        visits.add(visite);
    }

    //methode pour rajouter un antecedent
    public void addAntecedent(Antecedent antecedent){
        antecedents.add(antecedent);
    }



    //parametrized constructor
    public Folder(Patient patient){
        this.owner = patient;
    }


    //getters
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
