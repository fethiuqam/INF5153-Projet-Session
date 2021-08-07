package com.uqam.model;

import com.uqam.dao.DataSource;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Session implements Authenticable {

    private Visit newVisit;
    private Antecedent newAntecedent;
    private User user;
    private Folder currentFolder;
    private DataSource dataSource;
    private boolean modified;
    private Folder originalFolder;

    public Session(DataSource dataSource) {
        this.dataSource = dataSource;
        modified = false;
    }


    @Override
    public boolean login(String username, String password) {
        user = dataSource.findByUsernameAndPassword(username, password);
        return user != null;
    }

    public boolean downloadFolder(String insuranceNumber) {
        originalFolder = dataSource.findById(insuranceNumber);
        if (originalFolder != null)
            currentFolder = originalFolder.clone();
        return currentFolder != null;
    }

    public boolean saveFolder() {
        boolean result = true;
        if (modified && currentFolder != null) {
            if (newVisit != null){
                currentFolder.getVisits().add(newVisit);
            }
            if (newAntecedent != null){
                currentFolder.getAntecedents().add(newAntecedent);
            }
            result = dataSource.update(currentFolder)
                    && dataSource.archiveModification(currentFolder);
            originalFolder = currentFolder.clone();
        }
        return result;
    }

    public Visit createNewVisit() {
        newVisit = new Visit();
        newVisit.setDate(new Date(new java.util.Date().getTime()));
        newVisit.setDoctor(this.user.getDoctor());
        modified = true;
        return newVisit;
    }

    public Antecedent createNewAntecedent() {
        newAntecedent = new Antecedent();
        newAntecedent.setPrescriber(this.user.getDoctor());
        if (newVisit != null){
            if(newVisit.getDiagnostic() != null){
                newAntecedent.setDiagnostic(newVisit.getDiagnostic());
            }
            if(newVisit.getTreatment() != null){
                newAntecedent.setTreatment(newVisit.getTreatment());
            }
        }
        modified = true;
        return newAntecedent;
    }

    public boolean resetFolder() {
        currentFolder = originalFolder.clone();
        modified = false;
        return true;
    }

    public Set<Antecedent> getMutableAntecedents() {
        Set<Antecedent> results = new HashSet<>();
        if (user != null && currentFolder != null) {
            for (Antecedent a : currentFolder.getAntecedents()) {
                if (a.getPrescriber().equals(user.getDoctor())) {
                    results.add(a);
                }
            }
        }
        return results;
    }

    public Set<Antecedent> getImmutableAntecedents() {
        Set<Antecedent> results = new HashSet<>();
        if (user != null && currentFolder != null) {
            for (Antecedent a : currentFolder.getAntecedents()) {
                if (!a.getPrescriber().equals(user.getDoctor())) {
                    results.add(a);
                }
            }
        }
        return results;
    }

    public Set<Visit> getMutableVisits() {
        Set<Visit> results = new HashSet<>();
        if (user != null && currentFolder != null) {
            for (Visit v : currentFolder.getVisits()) {
                if (v.getDoctor().equals(user.getDoctor())) {
                    results.add(v);
                }
            }
        }
        return results;
    }

    public Set<Visit> getImmutableVisits() {
        Set<Visit> results = new HashSet<>();
        if (user != null && currentFolder != null) {
            results = currentFolder.getVisits().stream()
                    .filter(v -> !v.getDoctor().equals(user.getDoctor()))
                    .collect(Collectors.toSet());
        }

//        if (user != null && currentFolder != null) {
//            for (Visit v : currentFolder.getVisits()) {
//                if (!v.getDoctor().equals(user.getDoctor())) {
//                    results.add(v);
//                }
//            }
//        }
        return results;
    }

    public Set<Treatment> getTreatments() {
        Set<Treatment> results = new HashSet<>();
        if (user != null && currentFolder != null) {
            results = currentFolder.getVisits().stream()
                    .map(v -> v.getTreatment())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        return results;
    }


}