package com.uqam.controller;

import com.uqam.model.Antecedent;
import com.uqam.model.Visit;

import java.util.Set;

public class Session implements Authenticable{

    @Override
    public boolean login(String username, String password) {
        return true;
    }

    public boolean downloadFolder(String insuranceNumber){

        return true;
    }

    public boolean saveFolder(){
        return true;
    }

    public boolean cerateNewVisit(){
        return true;
    }

    public boolean cerateNewAntecedent(){
        return true;
    }

    public boolean resetFolder(){
        return true;
    }

    public Set<Antecedent> getMutableAntecedents(){
        return null;
    }

    public Set<Antecedent> getImmutableAntecedents(){
        return null;
    }

    public Set<Visit> getMutableVisits(){
        return null;
    }

    public Set<Visit> getImmutableVisits(){
        return null;
    }


}