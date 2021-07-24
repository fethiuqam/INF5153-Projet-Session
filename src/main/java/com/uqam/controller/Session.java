package com.uqam.controller;

import com.uqam.dao.DataSource;
import com.uqam.model.Antecedent;
import com.uqam.model.Folder;
import com.uqam.model.User;
import com.uqam.model.Visit;

import java.util.HashSet;
import java.util.Set;

public class Session implements Authenticable{

    private Visit newVisit;
    private Antecedent newAntecedent;
    private User user;
    private Folder folder;
    private DataSource dataSource;

    public Session (){
        dataSource = new DataSource();
    }


    @Override
    public boolean login(String username, String password) {
        user = dataSource.findByUsernameAndPassword(username, password);
        return user != null;
    }

    public boolean downloadFolder(String insuranceNumber){
        folder = dataSource.findById(insuranceNumber);
        return folder != null;
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
        Set<Antecedent> results = new HashSet<>();
        if(user != null && folder != null){
            for (Antecedent a: folder.getAntecedents()) {
                if (a.getPrescriber().equals(user.getDoctor())){
                    results.add(a);
                }
            }
        }
        return results;
    }

    public Set<Antecedent> getImmutableAntecedents(){
        Set<Antecedent> results = new HashSet<>();
        if(user != null && folder != null){
            for (Antecedent a: folder.getAntecedents()) {
                if (!a.getPrescriber().equals(user.getDoctor())){
                    results.add(a);
                }
            }
        }
        return results;
    }

    public Set<Visit> getMutableVisits(){
        return null;
    }

    public Set<Visit> getImmutableVisits(){
        return null;
    }


}