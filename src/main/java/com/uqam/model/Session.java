package com.uqam.model;

import com.uqam.dao.DataSource;

import java.sql.Date;

import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class Session implements Authenticable, Observer {

    private static User user;
    private Folder currentFolder;
    private DataSource dataSource;
    private boolean modified;
    private Folder originalFolder;

    public Session(DataSource dataSource) {
        this.dataSource = dataSource;
        modified = false;
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

    public Folder getOriginalFolder() {
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

    public void reinitializeFolder(){
        currentFolder = null;
        originalFolder = null;
    }

    public boolean saveFolder() throws AppException {
        boolean result = true;
        if (modified && currentFolder != null) {
            result = dataSource.update(currentFolder)
                    && dataSource.archiveModification(currentFolder);
            originalFolder = currentFolder.duplicate();
        }
        return result;
    }

    public Visit createNewVisit(String diagnostic, String treatment, String summary, String notes) {
        Doctor doctor = user.getDoctor();
        Date date = new Date(new java.util.Date().getTime());
        return new VisitBuilder(doctor,date)
        .diagnostic(diagnostic)
        .treatment(treatment)
        .summary(summary)
        .notes(notes)
        .build();
    }

    public Antecedent createNewAntecedent (String diagnostic, String treatment, Date start, Date end){
        Doctor doctor = user.getDoctor();

        return new AntecedentBuilder(doctor)
                .diagnostic(diagnostic)
                .treatment(treatment)
                .dateStart(start)
                .dateEnd(end)
                .build();
    }

    public boolean resetFolder() {
        currentFolder = originalFolder.duplicate();
        modified = false;
        return true;
    }

    public Set<Antecedent> getAntecedents() {
        return currentFolder.getAntecedents();
    }

    public Set<Visit> getVisits() {
        return currentFolder.getVisits();
    }

    public Doctor getDoctor() {
        return user.getDoctor();
    }

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    void setModified(boolean modified) {
        this.modified = modified;
    }

    @Override
    public void update(Observable observable, Object o) {
        modified = true;
    }
}