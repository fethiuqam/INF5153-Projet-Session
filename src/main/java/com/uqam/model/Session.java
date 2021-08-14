package com.uqam.model;

import com.uqam.dao.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

public class Session implements Authenticable {

    private static User user;
    private Folder currentFolder;
    private DataSource dataSource;
    private Folder originalFolder;

    public Session(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    User getUser() {
        return user;
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

    DataSource getDataSource() {
        return dataSource;
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
        if (currentFolder != null && originalFolder != null && !currentFolder.equals(originalFolder)) {
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

    public Antecedent createNewAntecedent (String diagnostic, String treatment, LocalDate start, LocalDate end){
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
        return true;
    }



}