package com.uqam.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Visit {

    private Date date;
    private String summary;
    private List<String> notes= new ArrayList<String>();
    private Treatment treatment;
    private Diagnostic diagnostic;
    private Doctor doctor;

    public Visit(Date date, String summary, String note, Treatment treatment, Diagnostic diagnostic, Doctor doctor) {
        this.date = date;
        this.summary = summary;
        notes.add(note);
        this.treatment = treatment;
        this.diagnostic = diagnostic;
        this.doctor = doctor;
    }

    public Date getDate() {
        return date;
    }
    public String getSummary() {
        return summary;
    }
    public List<String> getNotes() {
        return notes;
    }
    public Treatment getTreatment() {
        return treatment;
    }
    public Diagnostic getDiagnostic() {
        return diagnostic;
    }
    public Doctor getDoctor() {
        return doctor;
    }
}
