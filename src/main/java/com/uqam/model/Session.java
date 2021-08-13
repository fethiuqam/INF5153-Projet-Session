package com.uqam.model;

import com.uqam.dao.DataSource;

import java.sql.Date;

import java.util.HashSet;
import java.util.Set;

public class Session implements Authenticable {

    private Visit newVisit;
    private Set<Antecedent> newAntecedents;
    private User user;
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
            currentFolder = originalFolder.duplicate();
        return currentFolder != null;
    }

    public boolean saveFolder() throws AppException {
        boolean result = true;
        if (modified && currentFolder != null) {
            if (newVisit != null) {
                currentFolder.getVisits().add(newVisit);
            }
            if (newAntecedents.size() > 0) {
                currentFolder.getAntecedents().addAll(newAntecedents);
            }
            result = dataSource.update(currentFolder)
                    && dataSource.archiveModification(currentFolder);
            originalFolder = currentFolder.duplicate();
        }
        return result;
    }

    public Visit createNewVisit(Diagnostic diagnostic, Treatment treatment, String summary, String notes) {
        Doctor doctor = this.user.getDoctor();
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

    public Antecedent createNewAntecedent() {
        Antecedent newAntecedent = new Antecedent();
//        newAntecedent.setPrescriber(this.user.getDoctor()); // a mettre dans le builder
        newAntecedents.add(newAntecedent);
        modified = true;
        return newAntecedent;
    }

    public boolean resetFolder() throws AppException {
        currentFolder = originalFolder.duplicate();
        modified = false;
        return true;
    }

    public Set<Antecedent> getAntecedents() {
        return new HashSet<>(currentFolder.getAntecedents());
    }

    public Set<Visit> getVisits() {
        return new HashSet<>(currentFolder.getVisits());
    }

    public Doctor getDoctor() {
        return user.getDoctor();
    }

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}