package com.uqam.model;

import com.uqam.dao.DataSource;

import java.sql.Date;

import java.util.HashSet;
import java.util.Set;

public class Session implements Authenticable {

    private Visit newVisit;
    private Set<Antecedent> newAntecedents;
    private static User user;
    private Folder currentFolder;
    private DataSource dataSource;
    private boolean modified;
    private Folder originalFolder;

    public Session(DataSource dataSource) {
        this.dataSource = dataSource;
        newAntecedents = new HashSet<>();
        modified = false;
    }


    Visit getNewVisit() {
        return newVisit;
    }

    Set<Antecedent> getNewAntecedents() {
        return newAntecedents;
    }

    User getUser() {
        return user;
    }

    public Doctor getDoctor() {
        return user.getDoctor();
    }

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    DataSource getDataSource() {
        return dataSource;
    }

    boolean isModified() {
        return modified;
    }

    Folder getOriginalFolder() {
        return originalFolder;
    }

    @Override
    public boolean login(String username, String password) throws AppException {
        user = dataSource.findByUsernameAndPassword(username, password);
        return user != null;
    }

    public boolean downloadFolder(String insuranceNumber) throws AppException {
        originalFolder = dataSource.findById(insuranceNumber);
        if (originalFolder != null)
            currentFolder = originalFolder.clone();
        return currentFolder != null;
    }

    public void reinitializeFolder(){
        currentFolder = null;
    }

    public boolean saveFolder() throws AppException {
        boolean result = true;
        if (modified && currentFolder != null) {
            if (newVisit != null) {
                currentFolder.getVisits().add(newVisit);
                System.out.println("saved new visit");
            }
            if (newAntecedents.size() > 0) {
                currentFolder.getAntecedents().addAll(newAntecedents);
            }
            result = dataSource.update(currentFolder)
                    && dataSource.archiveModification(currentFolder);
            originalFolder = currentFolder.clone();
        }
        return result;
    }

    public Visit createNewVisit(String diagnostic, String treatment, String summary, String notes) {
        Doctor doctor = user.getDoctor();
        Date date = new Date(new java.util.Date().getTime());
        newVisit = new Visit.VisitBuilder(doctor,date)
        .diagnostic(diagnostic)
        .treatment(treatment)
        .summary(summary)
        .notes(notes)
        .build();

        modified = true;
        return newVisit;
    }

    // Modif - HAMZA
    public void deleteVisit (Visit visit){
        currentFolder.getVisits().remove(visit);
        modified =true;
    }

    public Antecedent createNewAntecedent() {
        Antecedent newAntecedent = new Antecedent();
//        newAntecedent.setPrescriber(this.user.getDoctor()); // a mettre dans le builder
        newAntecedents.add(newAntecedent);
        modified = true;
        return newAntecedent;
    }

    public boolean resetFolder() {
        currentFolder = originalFolder.clone();
        modified = false;
        return true;
    }

    public Set<Antecedent> getAntecedents() {
        return currentFolder.getAntecedents();
    }

    public Set<Visit> getVisits() {
        return currentFolder.getVisits();
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}